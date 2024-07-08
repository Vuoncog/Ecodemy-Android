package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Keyword

interface SearchResultDataProcess {
    suspend fun searchCourse(
        keyword: String,
        filterKeywords: String,
        onSuccessApi: (List<Course>) -> Unit,
    )

    suspend fun searchTeacher(
        keyword: String,
        onSuccessApi: (List<User>) -> Unit,
    )

    suspend fun searchGetKeywords(
        onSuccessApi: (List<Keyword>) -> Unit,
    )
}