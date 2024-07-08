package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.UserRecommendationCourse
import retrofit2.HttpException

interface CourseDataProcess {
    suspend fun getCourse(
        courseId: String,
        onSuccessApi: (Course) -> Unit,
    )

    suspend fun getPopularCourses(
        limit: Int = 5,
        onSuccessApi: (List<Course>) -> Unit,
    )

    suspend fun getCourseWithCategory(
        category: String,
        onSuccessApi: (List<Course>) -> Unit,
        onError: (HttpException) -> Unit,
    )

    suspend fun postReview(review: Review, onSuccess: () -> Unit)

    suspend fun getReviews(
        courseId: String,
        onSuccessApi: (List<Review>) -> Unit
    )

    suspend fun getUserReview(courseId: String, studentId: String, onSuccessApi: (Review?) -> Unit)

    suspend fun editReview(review: Review, onSuccess: () -> Unit)

    suspend fun getRecommendationCourses(
        ownerId: String,
        onSuccess: (UserRecommendationCourse) -> Unit
    )
}