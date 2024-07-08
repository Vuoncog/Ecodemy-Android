package com.kltn.ecodemy.domain.models.otp

data class OTPRequest(
    val userEmail: String,
    val number: String
)
