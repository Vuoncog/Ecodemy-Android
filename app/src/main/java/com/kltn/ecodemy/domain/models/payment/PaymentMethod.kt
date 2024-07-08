package com.kltn.ecodemy.domain.models.payment

import com.kltn.ecodemy.domain.models.zalo.ZaloOrderData

interface PaymentMethod {
    val method: String
}

data class ZaloMethod(
    override val method: String = "",
    val zaloOrderData: ZaloOrderData = ZaloOrderData(courseTitle = "", amount = "")
) : PaymentMethod
