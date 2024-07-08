package com.kltn.ecodemy.domain.repository

import com.google.gson.JsonElement
import com.kltn.ecodemy.domain.models.UpdateUserDataResponse
import com.kltn.ecodemy.domain.models.course.Course

interface WishlistDataProcess {
    suspend fun getWishlist(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit,
    )
    suspend fun updateWishlist(
        ownerId: String,
        courseId: String,
        action: String,
        onSuccessApi: (UpdateUserDataResponse) -> Unit
    )

    suspend fun updateRecommenderCoursesForUser(ownerId: String, courseId: String)
}