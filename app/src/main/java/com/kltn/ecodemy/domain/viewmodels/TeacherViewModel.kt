package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant.TEACHER_ID
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(
    val ecodemyApi: EcodemyApi,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val teacherId = savedStateHandle.get<String>(TEACHER_ID)
    private val _teacherUiState: MutableStateFlow<RequestState<TeacherUiState>> =
        MutableStateFlow(RequestState.Idle)
    val teacherUiState = _teacherUiState.asStateFlow()

    private val data: MutableState<TeacherUiState> = mutableStateOf(TeacherUiState())

    init {
        viewModelScope.launch {
            try {
                if (teacherId != null) {
                    val teacher = ecodemyApi.getSelectedUser(id = teacherId)
                    if (teacher.isNotEmpty()) {
                        data.value = data.value.copy(
                            user = teacher.first()
                        )
                        val courses = ecodemyApi.getTeacherCourse(teacherId)
                        data.value = data.value.copy(
                            listCourses = courses
                        )
                    }
                    getTeacher(teacher = data.value)
                }
            } catch (e: Exception) {
                getTeacher(teacher = data.value)
                Log.d("Retrofit error", e.toString())
            }

        }
    }

    private fun getTeacher(teacher: TeacherUiState) {
        _teacherUiState.value = RequestState.Success(
            data = teacher
        )
    }
}

data class TeacherUiState(
    val user: User = User(),
    val listCourses: List<Course> = emptyList()
)