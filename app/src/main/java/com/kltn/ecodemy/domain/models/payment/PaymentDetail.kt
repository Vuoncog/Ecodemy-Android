package com.kltn.ecodemy.domain.models.payment

data class PaymentDetail(
    val paymentMethod: PaymentMethod,
    val date: String,
    val courseId: String,
    val userId: String,
)