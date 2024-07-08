package com.kltn.ecodemy.domain.models.zalo

data class ZaloOrderResponse(
    val return_code: Int = 0,
    val return_message: String ="",
    val sub_return_code: Int =0,
    val sub_return_message: String ="",
    val order_url: String ="",
    val zp_trans_token: String = "",
    val order_token: String = "",
    val qr_code: String ="",
)
