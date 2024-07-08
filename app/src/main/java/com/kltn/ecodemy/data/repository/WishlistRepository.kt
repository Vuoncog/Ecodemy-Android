package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.domain.models.UpdateUserDataResponse
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.WishlistDataProcess
import javax.inject.Inject

class WishlistRepository @Inject constructor(
    private val wishlistDataProcess: WishlistDataProcess,
) {
    suspend fun getWishlist(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit
    ) = wishlistDataProcess.getWishlist(
        ownerId = ownerId,
        onSuccessApi = onSuccessApi
    )

    suspend fun updateWishlist(
        ownerId: String,
        courseId: String,
        action: String,
        onSuccessApi: (UpdateUserDataResponse) -> Unit
    ) = wishlistDataProcess.updateWishlist(
        ownerId = ownerId,
        courseId = courseId,
        action = action,
        onSuccessApi = onSuccessApi
    )

    suspend fun updateRecommenderCoursesForUser(
        ownerId: String,
        courseId: String,
    ) = wishlistDataProcess.updateRecommenderCoursesForUser(ownerId, courseId)
}