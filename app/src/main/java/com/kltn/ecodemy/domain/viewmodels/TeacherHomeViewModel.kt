package com.kltn.ecodemy.domain.viewmodels
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.TeacherRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherHomeViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val teacherHomeRepository: TeacherRepository,
) : ViewModel() {
    private val _teacherHomeUiState: MutableStateFlow<TeacherHomeUiState> = MutableStateFlow(TeacherHomeUiState())
    val teacherHomeUiState = _teacherHomeUiState.asStateFlow()


    init {
        initData()
    }

    fun refresh(){
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            try {
                fetchTeacherHomeData()
            }   catch (e: Exception) {
                Log.e("bngoc", e.toString())
            }
        }
    }
    private suspend fun setUser(){
        authenticationRepository.refreshUser()
        authenticationRepository.getUser {
            _teacherHomeUiState.value = _teacherHomeUiState.value.copy(
                user = it
            )
        }
    }

    private suspend fun fetchTeacherHomeData() {
        try {
            setUser()
            Log.d("bngoc", "initData: ${_teacherHomeUiState.value.user?.ownerId}")
            _teacherHomeUiState.value.user?.let {
                teacherHomeRepository.getOwnCourse(ownerId = it.ownerId) {
                    setAllTeacherCourse(courses = it)
                }
            }
        } catch (e: Exception) {
            Log.e("bngoc", e.toString())
        }
    }

    private fun setAllTeacherCourse(courses: List<Course>) {
        if (courses.isNotEmpty()) {
            _teacherHomeUiState.value = _teacherHomeUiState.value.copy(
                courses = RequestState.Success(data = courses),
            )
        } else {
            _teacherHomeUiState.value = _teacherHomeUiState.value.copy(
                courses = RequestState.Idle,
            )
        }
    }
}

data class TeacherHomeUiState(
    val courses: RequestState<List<Course>> = RequestState.Loading,
    val user: User? = null
)