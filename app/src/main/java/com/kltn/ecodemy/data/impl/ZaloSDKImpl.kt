package com.kltn.ecodemy.data.impl

import android.app.Activity
import android.content.Context
import android.util.Log
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.payment.PaymentDetail
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.domain.repository.ZaloSDK
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import javax.inject.Inject

class ZaloSDKImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi
) : ZaloSDK {
    override fun payOrder(
        token: String,
        activity: Activity,
        context: Context,
        onPaymentSucceeded: () -> Unit,
        onPaymentCanceled: () -> Unit,
        onPaymentError: () -> Unit,
    ) {
        ZaloPaySDK.getInstance().payOrder(
            activity,
            token,
            "eco://app",
            object : PayOrderListener {
                override fun onPaymentSucceeded(p0: String?, p1: String?, p2: String?) {
                    //p0: transactionId, p1: transToken, p2: appTransID
                    Log.d("ZaloPayment", "Successful")
                    onPaymentSucceeded()
                }

                override fun onPaymentCanceled(p0: String?, p1: String?) {
                    //p0: zpTransToken, p1: appTransID
                    onPaymentCanceled()
                }

                override fun onPaymentError(
                    p0: ZaloPayError?,
                    p1: String?,
                    p2: String?
                ) {
                    //p0: zaloPayError, p1: zpTransToken, p2: appTransID
                    onPaymentError()
                    Log.d("ZaloPayment", "Error")
                }
            }
        )
    }

    override suspend fun addUser(
        ownerId: String,
        courseId: String
    ) {
        ecodemyApi.updateCourseOfUser(
            ownerId = ownerId,
            courseId = courseId
        )
        ecodemyApi.upgradeRole(
            ownerId = ownerId,
            role = Role.Student.name
        )
        ecodemyApi.updateRecommenderCoursesForUser(
            ownerId = ownerId,
            courseId = courseId
        )
    }

    override suspend fun insertPaymentToSystem(paymentDetail: PaymentDetail) {
        ecodemyApi.insertPaymentHistory(paymentDetail = paymentDetail)
    }
}

