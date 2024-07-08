package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant.TEACHER_ROLE
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ecodemyApi: EcodemyApi,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        Log.d("HomeVM", "Init")
        refresh()
    }

    fun refresh() {
        initData()
    }

    private fun initData() {
        _homeUiState.value = _homeUiState.value.copy(
            courses = RequestState.Loading,
            showBottomBar = false
        )
        viewModelScope.launch {
            try {
                val coursesResponse = ecodemyApi.getPopularCourse()
                val latestCoursesResponse = ecodemyApi.getAllCourses().take(5)
                val teachersResponse = ecodemyApi.getSelectedUser(role = TEACHER_ROLE)
                setAllCourses(
                    courses = coursesResponse,
                    latestCourses = latestCoursesResponse,
                    adsList = latestCoursesResponse.map {
                        Ads(
                            images = it.poster,
                            courseId = it._id
                        )
                    }
                )
                setTeachers(teachers = teachersResponse)
                setUser()
            } catch (e: Exception) {
                _homeUiState.value = _homeUiState.value.copy(
                    courses = RequestState.Error(e),
                    showBottomBar = false
                )
            }
        }
    }

    fun setUser() {
        viewModelScope.launch {
            authenticationRepository.refreshUser()
            authenticationRepository.getUser {
                _homeUiState.value = _homeUiState.value.copy(
                    user = it
                )
            }
        }
    }

    private fun setAllCourses(
        courses: List<Course>,
        latestCourses: List<Course>,
        adsList: List<Ads>,
    ) {
        if (courses.isNotEmpty()) {
            _homeUiState.value = _homeUiState.value.copy(
                courses = RequestState.Success(data = courses),
                latestCourses = latestCourses,
                adsList = adsList,
                showBottomBar = true
            )
        } else {
            _homeUiState.value = _homeUiState.value.copy(
                courses = RequestState.Idle,
                showBottomBar = true
            )
        }
    }

    private fun setTeachers(teachers: List<User>) {
        _homeUiState.value = _homeUiState.value.copy(
            teachers = teachers
        )
    }
}

data class HomeUiState(
    val courses: RequestState<List<Course>> = RequestState.Loading,
    val latestCourses: List<Course> = emptyList(),
    val adsList: List<Ads> = emptyList(),
    val teachers: List<User> = emptyList(),
    val user: User? = null,
    val showBottomBar: Boolean = false,
)

data class Ads(
    val images: String,
    val courseId: String,
)