package com.kltn.ecodemy.domain.repository

import android.app.Activity
import android.content.Context
import com.kltn.ecodemy.domain.models.payment.PaymentDetail


interface ZaloSDK {
    fun payOrder(
        token: String,
        activity: Activity,
        context: Context,
        onPaymentSucceeded: () -> Unit,
        onPaymentCanceled: () -> Unit,
        onPaymentError: () -> Unit
    )

    suspend fun addUser(
        ownerId: String,
        courseId: String
    )

    suspend fun insertPaymentToSystem(paymentDetail: PaymentDetail)
}