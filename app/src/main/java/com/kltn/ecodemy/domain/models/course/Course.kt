package com.kltn.ecodemy.domain.models.course

import com.google.gson.annotations.SerializedName
import com.kltn.ecodemy.domain.models.user.User

data class Course(
    val _id: String = "",
    val type: Boolean = true,
    val price: Double = 0.0,
    val salePrice: Double = 0.0,
    val reviews: List<String> = emptyList(),
    val teacherId: String = "",
    val resourceId: String = "",
    val category: List<String> = emptyList(),
    val title: String = "",
    val poster: String = "",
    val rate: Double = 0.0,
    val rateCount: Int = 0,
    @SerializedName("teacherData")
    val teacher: User = User(),
    val about: About = About(),
    val lecture: Lecture = Lecture(),
    //special val for user
    val wishlistStatus: Boolean = false,
    val purchaseStatus: Boolean = false,
    var progress: String = "",
)

data class Lecture(
    val _id: String = "",
    val sections: List<Section> = emptyList()
)

data class Section(
    val title: String = "",
    val lessons: List<Lesson> = emptyList(),
)

data class Lesson(
    val linkVideo: String = "",
    val title: String = "",
    val resource: String = ""
)

data class Keyword(
    val _id: String = "",
    val name: String = "",
    val parentCategory: String = "",
)