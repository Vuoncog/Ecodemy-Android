package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.LearnDataProcess
import javax.inject.Inject

class LearnRepository @Inject constructor(
    private val learnDataProcess: LearnDataProcess
) {
    suspend fun getLearn(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit
    ) = learnDataProcess.getLearn(
        ownerId = ownerId,
        onSuccessApi = onSuccessApi
    )
}