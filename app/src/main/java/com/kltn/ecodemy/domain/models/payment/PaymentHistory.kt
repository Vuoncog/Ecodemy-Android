package com.kltn.ecodemy.domain.models.payment

import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.User

data class PaymentHistory(
    val _id: String = "",
    val courseId: String = "",
    val date: String = "",
    val paymentMethod: ZaloMethod = ZaloMethod(),
    val userId: String = "",
    val userData: User = User(),
    val courseData: Course = Course(),
    val teacherData: User = User(),
)