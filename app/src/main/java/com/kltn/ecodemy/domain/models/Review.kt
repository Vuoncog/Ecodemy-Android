package com.kltn.ecodemy.domain.models

import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.User

data class Review(
    val _id: String? = "",
    val content: String = "",
    val rate: Int = 0,
    val title: String = "",
    val courseId: String = "",
    val studentId: String = "",
    val studyStatus: String = "",
    val courseData: Course? = Course(),
    val userData: User? = User(),
)