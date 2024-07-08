package com.kltn.ecodemy.data.impl

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Lecture
import com.kltn.ecodemy.domain.models.course.UserRecommendationCourse
import com.kltn.ecodemy.domain.repository.CourseDataProcess
import retrofit2.HttpException
import javax.inject.Inject

class CourseDataProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
) : CourseDataProcess {

    override suspend fun getReviews(courseId: String, onSuccessApi: (List<Review>) -> Unit) {
        try {
            val reviewsResponse = ecodemyApi.getReviews(courseId = courseId)
            onSuccessApi(reviewsResponse)
        } catch (e: Exception) {
            onSuccessApi(emptyList())
        }
    }

    override suspend fun getRecommendationCourses(
        ownerId: String,
        onSuccess: (UserRecommendationCourse) -> Unit
    ) {
        try {
            val response = ecodemyApi.getRecommendCourses(ownerId = ownerId).first()
            val userRecommendationCourse =
                UserRecommendationCourse(latestCourse = response.lastestCourse)
            val courses = mutableListOf<Course>()
            val category = mutableListOf<String>()
            response.courseRecommendList.forEach {
                this.getCourse(it, onSuccessApi = { course ->
                    courses.add(course)
                    category.addAll(course.category)
                })
                if (it == response.courseRecommendList.last()) {
                    onSuccess(
                        userRecommendationCourse.copy(
                            courses = courses,
                            category = category.distinct()
                        )
                    )
                }
            }
        } catch (e: Exception) {
            onSuccess(UserRecommendationCourse(latestCourse = ""))
        }
    }

    override suspend fun getUserReview(
        courseId: String,
        studentId: String,
        onSuccessApi: (Review?) -> Unit
    ) {
        try {
            val reviewsResponse = ecodemyApi.getReviews(
                courseId = courseId,
                studentId = studentId
            )
            onSuccessApi(reviewsResponse.first())
        } catch (e: Exception) {
            onSuccessApi(null)
        }
    }

    override suspend fun getCourse(
        courseId: String,
        onSuccessApi: (Course) -> Unit,
    ) {
        try {
            val course = mutableStateOf(Course())
            val courseResponse = ecodemyApi.getSelectedCourse(id = courseId)
            if (courseResponse != null) {
                course.value = courseResponse
                getLecture(
                    resourceId = courseResponse.resourceId,
                    onSuccessApi = {
                        course.value = course.value.copy(
                            lecture = it
                        )
                    }
                )
                onSuccessApi(course.value)
            }
        } catch (e: Exception) {
            Log.d("Retrofit error CourseDataProcessImpl", e.toString())
        }
    }

    override suspend fun getPopularCourses(limit: Int, onSuccessApi: (List<Course>) -> Unit) {
        try {
            val courseResponse = ecodemyApi.getPopularCourse(limit = limit)
            onSuccessApi(courseResponse)
        } catch (e: HttpException) {
            onSuccessApi(emptyList())
            Log.d("Retrofit error CourseDataProcessImpl", e.toString())
        }
    }

    override suspend fun getCourseWithCategory(
        category: String,
        onSuccessApi: (List<Course>) -> Unit,
        onError: (HttpException) -> Unit,
    ) {
        try {
            val courseResponse = ecodemyApi.getCoursesWithCategory(category = category)
            onSuccessApi(courseResponse)
        } catch (e: HttpException) {
            onError(e)
            Log.d("Retrofit error CourseDataProcessImpl", e.toString())
        }
    }

    private suspend fun getTeacher(
        teacherId: String,
        onSuccessApi: (User) -> Unit
    ) {
        try {
            val teacher = ecodemyApi.getSelectedUser(id = teacherId)
            if (teacher.isNotEmpty()) {
                onSuccessApi(teacher.first())
            }
        } catch (e: Exception) {
            Log.d("Retrofit error CourseDataProcessImpl getTeacher", e.toString())
        }
    }

    private suspend fun getLecture(
        resourceId: String,
        onSuccessApi: (Lecture) -> Unit
    ) {
        val lectures = ecodemyApi.getLectures(id = resourceId)
        if (lectures.isNotEmpty()) {
            onSuccessApi(lectures.first())
        }
    }

    override suspend fun postReview(review: Review, onSuccess: () -> Unit) {
        try {
            val response = ecodemyApi.postReview(review)
            if (response.matchedCount == 0) {
                onSuccess()
            }
        } catch (e: Exception) {
            Log.d("Retrofit error post review", e.toString())
        }
    }

    override suspend fun editReview(review: Review, onSuccess: () -> Unit) {
        try {
            val response = ecodemyApi.editReview(
                studentId = review.studentId,
                courseId = review.courseId,
                rate = review.rate,
                title = review.title,
                content = review.content
            )
            if (response.matchedCount == 1) {
                onSuccess()
            }
        } catch (e: Exception) {
            Log.d("Retrofit error post review", e.toString())
        }
    }
}