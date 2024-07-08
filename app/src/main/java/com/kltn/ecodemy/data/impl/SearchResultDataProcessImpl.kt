package com.kltn.ecodemy.data.impl

import android.util.Log
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Keyword
import com.kltn.ecodemy.domain.repository.SearchResultDataProcess
import javax.inject.Inject

class SearchResultDataProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
) : SearchResultDataProcess {
    override suspend fun searchCourse(
        keyword: String,
        filterKeywords: String,
        onSuccessApi: (List<Course>) -> Unit,
    ) {
        try {
            val response = ecodemyApi.getSearchCourse(keyword, filterKeywords)
            val addCourses = mutableListOf<Course>()
            if (response.isNotEmpty()) {
                response.forEach { course ->
                    val selectedCourse = ecodemyApi.getSelectedCourse(id = course._id)
                    val teacher = ecodemyApi.getSelectedUser(id = course.teacherId)
                    if (selectedCourse != null && teacher.isNotEmpty()) {
                        addCourses.add(selectedCourse.copy(teacher = teacher.first()))
                    }
                }
            }
            onSuccessApi(addCourses)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun searchTeacher(
        keyword: String,
        onSuccessApi: (List<User>) -> Unit
    ) {
        try {
            val response = ecodemyApi.getSearchUser(keyword)
            onSuccessApi(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun searchGetKeywords(
        onSuccessApi: (List<Keyword>) -> Unit
    ) {
        try {
            val response = ecodemyApi.getSearchKeyword()
            onSuccessApi(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}