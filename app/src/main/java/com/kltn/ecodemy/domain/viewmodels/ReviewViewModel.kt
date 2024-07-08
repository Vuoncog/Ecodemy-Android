package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant.OWNER_ID
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.STUDENT_ID
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private val _courseId = savedStateHandle.get<String>(COURSE_ID)
    private val _studentId = savedStateHandle.get<String>(STUDENT_ID)

    private val _reviewUiState = MutableStateFlow(ReviewUiState())
    val reviewUiState = _reviewUiState.asStateFlow()

    private val _title = MutableStateFlow(TextFieldValue())
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow(TextFieldValue())
    val content = _content.asStateFlow()

    init {
        getUserReview()
        setUpDebounce()
    }

    private fun getUserReview() {
        viewModelScope.launch {
            if (_courseId != null && _studentId != null)
                courseRepository.getUserReview(_courseId, _studentId) {
                    _reviewUiState.value = _reviewUiState.value.copy(
                        review = it,
                    )
                    if (it != null) {
                        _title.value = TextFieldValue(text = it.title)
                        _content.value = TextFieldValue(text = it.content)
                    }
                }
        }
    }

    private fun setUpDebounce() {
        viewModelScope.launch {
            _title.debounce(1).collect {
                _reviewUiState.value = _reviewUiState.value.copy(
                    titleError = false,
                    contentError = false
                )
            }

        }
        viewModelScope.launch {
            _content.debounce(1).collect {
                _reviewUiState.value = _reviewUiState.value.copy(
                    titleError = false,
                    contentError = false
                )
            }
        }
    }

    fun setTitle(value: TextFieldValue) {
        _title.value = value
    }

    fun setContent(value: TextFieldValue) {
        _content.value = value
    }

    fun postReview(rate: Int, onSuccess: () -> Unit, onFailed: () -> Unit) {
        val content = _content.value.text
        val title = _title.value.text
        if (title.isEmpty() || content.isEmpty()) {
            _reviewUiState.value = _reviewUiState.value.copy(
                titleError = title.isEmpty(),
                contentError = content.isEmpty()
            )
            onFailed()
            return
        }
        val review = Review(
            _id = null,
            content = content,
            title = title,
            rate = rate,
            courseId = _courseId ?: "",
            studentId = _studentId ?: "",
            studyStatus = "In progress",
            courseData = null,
            userData = null
        )
        viewModelScope.launch {
            courseRepository.postReview(review, onSuccess = onSuccess)
        }
    }

    fun editReview(rate: Int, onSuccess: () -> Unit, onFailed: () -> Unit) {
        val content = _content.value.text
        val title = _title.value.text
        if (title.isEmpty() || content.isEmpty()) {
            _reviewUiState.value = _reviewUiState.value.copy(
                titleError = title.isEmpty(),
                contentError = content.isEmpty()
            )
            onFailed()
            return
        }
        val review = Review(
            _id = null,
            content = content,
            title = title,
            rate = rate,
            courseId = _courseId ?: "",
            studentId = _studentId ?: "",
            studyStatus = "In progress",
            courseData = null,
            userData = null
        )
        viewModelScope.launch {
            courseRepository.editReview(review, onSuccess = onSuccess)
        }
    }
}

data class ReviewUiState(
    val titleError: Boolean = false,
    val contentError: Boolean = false,
    val review: Review? = null,
)
