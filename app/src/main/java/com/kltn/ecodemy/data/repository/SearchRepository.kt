package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Keyword
import com.kltn.ecodemy.domain.models.course.UserRecommendationCourse
import com.kltn.ecodemy.domain.repository.CourseDataProcess
import com.kltn.ecodemy.domain.repository.SearchResultDataProcess
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchResultDataProcess: SearchResultDataProcess,
    private val courseDataProcess: CourseDataProcess,
) {
    suspend fun searchCourse(
        keyword: String,
        filterKeywords: String,
        onSuccessApi: (List<Course>) -> Unit,
    ) = searchResultDataProcess.searchCourse(
        keyword = keyword,
        filterKeywords = filterKeywords,
        onSuccessApi = onSuccessApi
    )

    suspend fun getPopularCourses(
        limit: Int = 5,
        onSuccessApi: (List<Course>) -> Unit,
    ) = courseDataProcess.getPopularCourses(limit, onSuccessApi)


    suspend fun getRecommendCourses(
        ownerId: String,
        onSuccess: (UserRecommendationCourse) -> Unit
    ) = courseDataProcess.getRecommendationCourses(
        ownerId = ownerId,
        onSuccess = onSuccess
    )

    suspend fun searchTeacher(
        keyword: String,
        onSuccessApi: (List<User>) -> Unit,
    ) = searchResultDataProcess.searchTeacher(
        keyword = keyword,
        onSuccessApi = onSuccessApi
    )

    suspend fun searchGetKeywords(
        onSuccessApi: (List<Keyword>) -> Unit,
    ) = searchResultDataProcess.searchGetKeywords(
        onSuccessApi = onSuccessApi
    )
}