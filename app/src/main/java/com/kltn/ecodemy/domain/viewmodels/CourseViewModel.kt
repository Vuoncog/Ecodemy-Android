package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.data.repository.LearnRepository
import com.kltn.ecodemy.data.repository.WishlistRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val wishlistRepository: WishlistRepository,
    private val learnRepository: LearnRepository,
) : ViewModel() {
    private val _courseUiState: MutableStateFlow<RequestState<CourseUiState>> =
        MutableStateFlow(RequestState.Loading)
    val courseUiState = _courseUiState.asStateFlow()

    private val courseId = savedStateHandle.get<String>(key = COURSE_ID)
    private val data = mutableStateOf(CourseUiState())

    private val _ownerId: MutableState<String> = mutableStateOf("")
    private val _studentId: MutableState<String> = mutableStateOf("")

    val firstCourse: MutableState<Boolean?> = mutableStateOf(null)

    private val _userReview: MutableState<Review?> = mutableStateOf(null)
    val userReview = _userReview

    init {
        initialize()
        getReviews()
    }

    private fun initialize() {
        viewModelScope.launch {
            authenticationRepository.getUser {
                if (it.ownerId.isEmpty()) {
                    return@getUser
                }
                _ownerId.value = it.ownerId
                _studentId.value = it._id ?: ""
                firstCourse.value = it.userCourses.courseInfo.isEmpty()
            }
            if (courseId != null) {
                courseRepository.getCourse(courseId = courseId) {
                    data.value = data.value.copy(
                        course = it
                    )
                    setPurchaseAndWishlistStatus(courseId)
//                    setCourse()
                }
            }
        }
    }

    fun getStudentId() = _studentId.value

    private fun getReviews() {
        if (courseId != null) {
            viewModelScope.launch {
                courseRepository.getReviews(courseId) {
                    data.value = data.value.copy(
                        reviews = it
                    )
                }
            }
        }
    }

    private fun setCourse() {
        if (data.value != CourseUiState()) {
            _courseUiState.value = RequestState.Success(
                data = data.value
            )
        } else {
            _courseUiState.value = RequestState.Idle
        }
    }

    fun updateWishlist(courseId: String, wishlistStatus: Boolean) {
        viewModelScope.launch {
            try {
                if (wishlistStatus) {
                    wishlistRepository.updateWishlist(_ownerId.value, courseId, "remove") {
                        data.value = data.value.copy(
                            course = data.value.course.copy(wishlistStatus = false)
                        )
                        setCourse()
                    }
                } else {
                    wishlistRepository.updateWishlist(_ownerId.value, courseId, "add") {
                        data.value = data.value.copy(
                            course = data.value.course.copy(wishlistStatus = true)
                        )
                        setCourse()
                    }
                    wishlistRepository.updateRecommenderCoursesForUser(
                        ownerId = _ownerId.value,
                        courseId = courseId
                    )
                }
            } catch (e: Exception) {
                Log.e("bngoc", e.toString())
            }
        }
    }

    private fun setPurchaseAndWishlistStatus(courseId: String) {
        viewModelScope.launch {
            try {
                if (_ownerId.value.isNotBlank()) {
                    wishlistRepository.getWishlist(_ownerId.value) {
                        val wishlistStatus = it.find { wishlist -> wishlist._id == courseId }
                        if (wishlistStatus != null) {
                            val updatedCourse = data.value.course.copy(wishlistStatus = true)
                            data.value = data.value.copy(course = updatedCourse)
                        }
                    }
                    learnRepository.getLearn(_ownerId.value) {
                        val purchaseStatus = it.find { learn -> learn._id == courseId }
                        if (purchaseStatus != null) {
                            val updatedCourse = data.value.course.copy(purchaseStatus = true)
                            data.value = data.value.copy(course = updatedCourse)
                            //Set review
                            getUserReview(courseId)
                        }
                    }
                    setCourse()
                } else {
                    setCourse()
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }

    private fun getUserReview(courseId: String) {
        viewModelScope.launch {
            courseRepository.getUserReview(
                courseId = courseId,
                studentId = _studentId.value
            ) {
                _userReview.value = it
            }
        }
    }
}

data class CourseUiState(
    val course: Course = Course(),
    val reviews: List<Review> = emptyList(),
)
