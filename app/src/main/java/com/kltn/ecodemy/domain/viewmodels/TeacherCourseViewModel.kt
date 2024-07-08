package com.kltn.ecodemy.domain.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherCourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val courseId = savedStateHandle.get<String>(COURSE_ID)
    private val _teacherCourseUiState = MutableStateFlow(TeacherCourseUiState())
    val teacherCourseUiState = _teacherCourseUiState.asStateFlow()
    private lateinit var resourceId: String

    init {
        getCourse()
    }

    private fun getCourse() {
        viewModelScope.launch {
            try {
                if (courseId != null) {
                    courseRepository.getCourse(courseId, onSuccessApi = {
                        resourceId = it.resourceId
                        _teacherCourseUiState.value = _teacherCourseUiState.value.copy(
                            course = RequestState.Success(it)
                        )
                    })
                }
            } catch (e: Exception) {
                _teacherCourseUiState.value = _teacherCourseUiState.value.copy(
                    course = RequestState.Error(e)
                )
            }
        }
    }

    fun loading(loading: Boolean) {
        _teacherCourseUiState.value = _teacherCourseUiState.value.copy(
            isLoading = loading
        )
    }

    fun addResources(
        sectionIndex: Int,
        lessonIndex: Int,
        uri: Uri,
    ) {
        val list = _teacherCourseUiState.value.resources
        list.add(ResourceIndex(uri, lessonIndex, sectionIndex))
        _teacherCourseUiState.value = _teacherCourseUiState.value.copy(
            resources = list
        )
    }

    fun uploadResources(
        onSuccess: () -> Unit,
    ) {
        if (courseId != null) {
            val list = _teacherCourseUiState.value.resources
            Log.d("ListRs", list.toString())
            list.forEachIndexed { index, resourceIndex ->
                courseRepository.uploadFile(
                    uri = resourceIndex.uri,
                    courseId = courseId,
                    onSuccess = {
                        Log.d("UriRs", it.toString())
                        viewModelScope.launch {
                            courseRepository.addResourcesToLecture(
                                resourceId = resourceId,
                                resource = it.toString(),
                                sectionIndex = resourceIndex.sectionIndex,
                                lessonIndex = resourceIndex.lessonIndex
                            )
                        }
                    },
                    onFailure = {

                    }
                )
                if (index == list.size - 1) {
                    onSuccess()
                }
            }
        }
    }
}

data class TeacherCourseUiState(
    val course: RequestState<Course> = RequestState.Loading,
    val resources: MutableList<ResourceIndex> = mutableListOf(),
    val isLoading: Boolean = false,
)

data class ResourceIndex(
    val uri: Uri,
    val lessonIndex: Int,
    val sectionIndex: Int,
)