package com.kltn.ecodemy.data.impl

import android.util.Log
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.UpdateUserDataResponse
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.repository.WishlistDataProcess
import javax.inject.Inject

class WishlistDataProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
) : WishlistDataProcess {
    override suspend fun getWishlist(
        ownerId: String,
        onSuccessApi: (List<Course>) -> Unit,
    ) {
        try {
            val userResponse = ecodemyApi.getUserData(ownerId = ownerId, userWishlist = "true")
            val wishlistCourses = userResponse.firstOrNull()?.userWishlist?.courseId ?: emptyList()
            val addCourses = wishlistCourses.mapNotNull { wishlist ->
                val selectedCourse = ecodemyApi.getSelectedCourse(id = wishlist)
                ecodemyApi.getSelectedUser(id = selectedCourse?.teacherId).firstOrNull()
                    ?.let { selectedCourse?.copy(teacher = it) }
            }
//        Log.d("bngoc", addCourses.toString())
            onSuccessApi(addCourses)
        } catch (e: Exception) {
            Log.d("Retrofit error WishlistDataProcessImpl", e.toString())
        }
    }

    override suspend fun updateWishlist(
        ownerId: String,
        courseId: String,
        action: String,
        onSuccessApi: (UpdateUserDataResponse) -> Unit
    ) {
        try {
            val response = ecodemyApi.updateUserData(ownerId, courseId, action)
            onSuccessApi(response)
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }

    override suspend fun updateRecommenderCoursesForUser(
        ownerId: String,
        courseId: String,
    ) {
        ecodemyApi.updateRecommenderCoursesForUser(
            ownerId = ownerId,
            courseId = courseId
        )
    }
}