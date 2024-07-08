package com.kltn.ecodemy.data.repository

import android.net.Uri
import android.util.Log
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.data.api.SystemApi
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.CourseDataProcess
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import retrofit2.HttpException
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseDataProcess: CourseDataProcess,
    private val ecodemyApi: EcodemyApi,
    private val serviceApi: SystemApi,
    private val firebaseDataProcess: FirebaseDataProcess,
) {
    suspend fun getCourse(
        courseId: String,
        onSuccessApi: (Course) -> Unit
    ) = courseDataProcess.getCourse(
        courseId = courseId,
        onSuccessApi = onSuccessApi
    )

    suspend fun getCourseWithCategory(
        category: String,
        onSuccessApi: (List<Course>) -> Unit,
        onError: (HttpException) -> Unit,
    ) = courseDataProcess.getCourseWithCategory(category, onSuccessApi, onError)

    suspend fun getReviews(
        courseId: String,
        onSuccessApi: (List<Review>) -> Unit
    ) = courseDataProcess.getReviews(courseId, onSuccessApi)

    suspend fun getUserReview(
        courseId: String,
        studentId: String,
        onSuccessApi: (Review?) -> Unit
    ) = courseDataProcess.getUserReview(courseId, studentId, onSuccessApi)

    suspend fun postReview(
        review: Review,
        onSuccess: () -> Unit,
    ) = courseDataProcess.postReview(review, onSuccess)

    suspend fun editReview(
        review: Review,
        onSuccess: () -> Unit,
    ) = courseDataProcess.editReview(review, onSuccess)

    suspend fun addResourcesToLecture(
        resourceId: String,
        resource: String,
        sectionIndex: Int,
        lessonIndex: Int,
    ) {
        ecodemyApi.addResourcesToLecture(
            resourceId, resource, sectionIndex, lessonIndex
        )
    }

    fun uploadFile(
        uri: Uri,
        courseId: String,
        onSuccess: (Uri) -> Unit,
        onFailure: () -> Unit
    ) = firebaseDataProcess.uploadFile(uri, courseId, onSuccess, onFailure)

    suspend fun getRecommenderCourses(
        onSuccessApi: (List<Course>) -> Unit
    ) {
        try {
            val res = serviceApi.getAll().filter { it.itemsets.size == 1 }
            val coursesList = mutableListOf<Course>()
            val highGrade = res.filter { it.support > 0.1 }
            highGrade.forEachIndexed { index, recommenderResponse ->
                val courseRes =
                    ecodemyApi.getSelectedCourse(id = recommenderResponse.itemsets.first())
                courseRes?.let { course -> coursesList.add(course) }
                if (index == highGrade.size - 1) {
                    onSuccessApi(coursesList)
                }
            }
        } catch (e: Exception) {
            Log.d("Recommender", e.toString())
        }
    }

    suspend fun getInCommonCourses(
        userId: String,
        onSuccessApi: (List<Course>) -> Unit
    ) {
        try {
            val res = serviceApi.getInCommonCourse(userId)
            Log.d("vuoncog", res.toString())
            val coursesList = mutableListOf<Course>()
            res.forEachIndexed { index, courseId ->
                val courseRes =
                    ecodemyApi.getSelectedCourse(id = courseId)
                courseRes?.let { course -> coursesList.add(course) }
                if (index == res.size - 1) {
                    onSuccessApi(coursesList)
                }
            }
        } catch (e: Exception) {
            Log.d("Recommender", e.toString())
        }
    }

    suspend fun updateCourseProgress(
        ownerId: String,
        courseId: String,
        courseLesson: String
    ) = ecodemyApi.updateProgressCourse(ownerId, courseId, courseLesson)
}