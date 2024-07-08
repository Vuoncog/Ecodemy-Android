package com.kltn.ecodemy.data.impl

import android.util.Log
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.LearnDataProcess
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LearnDataProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
) : LearnDataProcess {
    override suspend fun getLearn(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit,
    ) {
        val userResponse = ecodemyApi.getUserData(ownerId = ownerId, userCourses = "true")
        val learnCourses = userResponse.firstOrNull()?.userCourses?.courseInfo ?: emptyList()
        val addCourses = learnCourses.mapNotNull { learn ->
            val selectedCourse: MutableStateFlow<Course?> = MutableStateFlow(null)
            selectedCourse.value = ecodemyApi.getSelectedCourse(id = learn.id)
            selectedCourse.value?.progress = learn.progress
            ecodemyApi.getSelectedUser(id = selectedCourse.value?.teacherId).firstOrNull()
                ?.let { selectedCourse.value?.copy(teacher = it) }

        }
        onSuccessApi(addCourses)
    }
}