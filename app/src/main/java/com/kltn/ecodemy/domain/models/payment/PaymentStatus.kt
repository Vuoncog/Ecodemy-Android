package com.kltn.ecodemy.domain.models.payment

sealed class PaymentStatus {
    data class SUCCESSFUL(val title: String): PaymentStatus()
    data object CANCELED: PaymentStatus()
    data object ERROR: PaymentStatus()
    data object IDLE: PaymentStatus()
}