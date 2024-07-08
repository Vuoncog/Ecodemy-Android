package com.kltn.ecodemy.domain.models.user

data class User(
    val _id: String? = "",
    val role: Role = Role.Guide,
    val createdDate: String = "",
    val userInfo: UserInfo = UserInfo(),
    val userWishlist: UserWishlist = UserWishlist(),
    val userCourses: UserCourses = UserCourses(),
    val ownerId: String = "",
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "_id" to _id,
            "role" to role,
            "createdDate" to createdDate,
            "userInfo" to userInfo,
            "userWishlist" to userWishlist,
            "userCourses" to userCourses,
            "ownerId" to ownerId,
        )
    }
}

data class UserInfo(
    val address: Address = Address(),
    val birth: String = "",
    val email: String = "",
    val phone: String = "",
    val fullName: String = "User",
    val avatar: String = ""
)

data class Address(
    val city: String = "",
    val street: String = "",
    val houseNumber: String = "",
    val zipCode: String = ""
)

data class UserWishlist(
    val courseId: List<String> = emptyList(),
)

data class UserCourses(
    val courseInfo: List<CourseInfo> = emptyList(),
)

data class CourseInfo(
    val id: String = "",
    val progress: String = "",
    val blankInfo: String = "",
)
