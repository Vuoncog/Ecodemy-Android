package com.kltn.ecodemy.data.api

import com.google.gson.JsonElement
import com.kltn.ecodemy.domain.models.EnrolledUserResponse
import com.kltn.ecodemy.domain.models.PaymentDetailResponse
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.UpdateUserDataResponse
import com.kltn.ecodemy.domain.models.UserPostResponse
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Keyword
import com.kltn.ecodemy.domain.models.course.Lecture
import com.kltn.ecodemy.domain.models.RecommendCourseResponse
import com.kltn.ecodemy.domain.models.payment.PaymentDetail
import com.kltn.ecodemy.domain.models.payment.PaymentHistory
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserContact
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface EcodemyApi {
    @GET("course")
    suspend fun getAllCourses(): List<Course>

    @GET("course")
    suspend fun getCoursesWithCategory(
        @Query("category") category: String
    ): List<Course>

    @GET("course")
    suspend fun getSelectedCourse(
        @Query("id") id: String
    ): Course?

    @GET("popularCourses")
    suspend fun getPopularCourse(
        @Query("limit") limit: Int = 5
    ): List<Course>

    @GET("userRecommend")
    suspend fun getRecommendCourses(
        @Query("ownerId") ownerId: String,
    ): List<RecommendCourseResponse>

    @GET("searchWithKeywords")
    suspend fun getSearchCourse(
        @Query("textSearch") textSearch: String,
        @Query("category") category: String? = null,
    ): List<Course>

    @GET("user")
    suspend fun getSelectedUser(
        @Query("id") id: String? = null,
        @Query("role") role: String? = null,
        @Query("email") email: String? = null,
        @Query("ownerId") ownerId: String? = null,
    ): List<User>

    @GET("user")
    suspend fun getUserData(
        @Query("id") id: String? = null,
        @Query("ownerId") ownerId: String? = null,
        @Query("userWishlist") userWishlist: String? = null,
        @Query("userCourses") userCourses: String? = null
    ): List<User>

    @GET("user")
    suspend fun getSearchUser(
        @Query("textSearch") textSearch: String? = null,
        @Query("role") role: String = "teacher",
    ): List<User>

    @GET("teacherCourse")
    suspend fun getTeacherCourse(
        @Query("teacherId") teacherId: String? = null,
    ): List<Course>

    @GET("courseRs")
    suspend fun getLectures(
        @Query("id") id: String? = null,
    ): List<Lecture>

    @PUT("userWishlist")
    suspend fun updateUserData(
//        @Query("id") id: String? = null,
        @Query("ownerId") ownerId: String? = null,
        @Query("courseId") courseId: String? = null,
        @Query("action") action: String? = null,
    ): UpdateUserDataResponse

    @POST("user")
    suspend fun insertNewUser(
        @Body user: User
    ): UserPostResponse

    @PUT("userCourse")
    suspend fun updateCourseOfUser(
//        @Query("id") id: String? = null,
        @Query("ownerId") ownerId: String? = null,
        @Query("courseId") courseId: String? = null,
    ): UpdateUserDataResponse

    @PUT("updateUserRole")
    suspend fun upgradeRole(
        @Query("ownerId") ownerId: String,
        @Query("role") role: String,
    ): PaymentDetailResponse

    @POST("payment")
    suspend fun insertPaymentHistory(
        @Body paymentDetail: PaymentDetail
    ): PaymentDetailResponse

    @POST("userRecommend")
    suspend fun updateRecommenderCoursesForUser(
        @Query("ownerId") ownerId: String,
        @Query("courseId") courseId: String,
    ): JsonElement

    @POST("enroll")
    suspend fun insertEnrolledCourseUser(
        @Body userContact: UserContact
    ): EnrolledUserResponse

    @GET("category")
    suspend fun getSearchKeyword(): List<Keyword>

    @GET("payment")
    suspend fun getPayment(
        @Query("userId") userId: String? = null
    ): List<PaymentHistory>

    @PUT("updateUserAvt")
    suspend fun updateUserAvatar(
        @Query("ownerId") ownerId: String,
        @Query("avtUrl") avtUrl: String
    ): UpdateUserDataResponse

    @GET("review")
    suspend fun getReviews(
        @Query("courseId") courseId: String,
        @Query("studentId") studentId: String? = null,
    ): List<Review>

    @POST("review")
    suspend fun postReview(
        @Body review: Review
    ): UpdateUserDataResponse

    @PUT("updateReview")
    suspend fun editReview(
        @Query("studentId") studentId: String,
        @Query("courseId") courseId: String,
        @Query("rate") rate: Int,
        @Query("title") title: String,
        @Query("content") content: String,
    ): UpdateUserDataResponse

    @PUT("updateCourseResource")
    suspend fun addResourcesToLecture(
        @Query("id") resourceId: String,
        @Query("resource") resource: String,
        @Query("sectionIndex") sectionIndex: Int,
        @Query("lessonIndex") lessonIndex: Int,
    ): UpdateUserDataResponse

    @PUT("updateProgress")
    suspend fun updateProgressCourse(
        @Query("ownerId") ownerId: String,
        @Query("courseId") courseId: String,
        @Query("courseLesson") courseLesson: String,
    )

}