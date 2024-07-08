package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.course.Course

interface TeacherHomeDataProcess {
    suspend fun getOwnCourse(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit,
    )
}