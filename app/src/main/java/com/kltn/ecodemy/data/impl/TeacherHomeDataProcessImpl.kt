package com.kltn.ecodemy.data.impl

import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.TeacherHomeDataProcess
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TeacherHomeDataProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
) : TeacherHomeDataProcess {
    override suspend fun getOwnCourse(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit,
    ) {
        val userResponse = ecodemyApi.getUserData(ownerId = ownerId, userCourses = "true")
        val ownCourses = userResponse.firstOrNull()?.userCourses?.courseInfo ?: emptyList()
        val addCourses = ownCourses.mapNotNull { ownCourse ->
            val selectedCourse: MutableStateFlow<Course?> = MutableStateFlow(null)
            selectedCourse.value = ecodemyApi.getSelectedCourse(id = ownCourse.id)
            selectedCourse.value?.progress = ownCourse.progress
            ecodemyApi.getSelectedUser(id = selectedCourse.value?.teacherId).firstOrNull()
                ?.let { selectedCourse.value?.copy(teacher = it) }
        }
        onSuccessApi(addCourses)
    }
}