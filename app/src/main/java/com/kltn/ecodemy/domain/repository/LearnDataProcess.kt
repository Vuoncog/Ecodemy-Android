package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.course.Course

interface LearnDataProcess {
    suspend fun getLearn(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit,
    )
}