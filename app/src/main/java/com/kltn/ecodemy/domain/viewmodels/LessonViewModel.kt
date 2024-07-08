package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant.SPLITTER
import com.kltn.ecodemy.constant.fromUrlArgumentToLink
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.domain.models.course.Lecture
import com.kltn.ecodemy.domain.models.course.Lesson
import com.kltn.ecodemy.domain.models.course.Section
import com.kltn.ecodemy.domain.models.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val courseId = savedStateHandle.get<String>(key = Route.Arg.COURSE_ID)
    private val sectionId = savedStateHandle.get<Int>(key = Route.Arg.SECTION_ID)
    private val lessonId = savedStateHandle.get<Int>(key = Route.Arg.LESSON_ID)

    private val _learnUiState = MutableStateFlow(LessonUiState())
    val learnUiState = _learnUiState.asStateFlow()

    init {
        setLecture()
        Log.d("ArgsLectureVM2", _learnUiState.value.lecture.toString())
    }

    private fun updateCourseProgress(selectedLesson: String) {
        viewModelScope.launch {
            authenticationRepository.getUser { user ->
                viewModelScope.launch {
                    courseId?.let {
                        courseRepository.updateCourseProgress(
                            ownerId = user.ownerId,
                            courseId = it,
                            courseLesson = "$courseId-$sectionId-$lessonId-$selectedLesson"
                        )
                    }
                }
            }
        }
    }

    private fun setLecture() {
        viewModelScope.launch {
            if (courseId != null) {
                courseRepository.getCourse(courseId = courseId) {
                    val lecture = it.lecture
                    Log.d("ArgSection", sectionId.toString())
                    Log.d("ArgLesson", lessonId.toString())
                    val isFirstSection = if (sectionId == 0) 1 else sectionId
                    val selectedLessonIndex =
                        ((lessonId)?.minus(sectionId ?: 0))?.div(isFirstSection ?: 1) ?: 1
                    val selectedSection = lecture.sections[sectionId?.minus(1) ?: 0]
                    val selectedLesson = selectedSection.lessons[selectedLessonIndex.minus(1)]
                    updateCourseProgress(selectedLesson.title)

                    Log.d("ArgSectionVM", isFirstSection.toString())
                    Log.d("ArgLessonVM", selectedLessonIndex.toString())
                    _learnUiState.value = _learnUiState.value.copy(
                        lecture = it.lecture,
                        sectionSelected = selectedSection,
                        lessonSelected = selectedLesson,
                        lessonIndex = lessonId ?: 1,
                        videoUrl = selectedLesson.linkVideo,
                        resources = listOf(selectedLesson.resource),
                        courseTitle = it.title,
                        courseTeacher = it.teacher
                    )
                }
            }
        }
    }
}

data class LessonUiState(
    val lecture: Lecture = Lecture(),
    val videoUrl: String = "no video",
    val resources: List<String> = emptyList(),
    val sectionSelected: Section = Section(),
    val lessonIndex: Int = 0,
    val lessonSelected: Lesson = Lesson(),
    val courseTitle: String = "",
    val courseTeacher: User = User()
)