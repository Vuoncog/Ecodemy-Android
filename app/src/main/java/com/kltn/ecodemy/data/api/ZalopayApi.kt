package com.kltn.ecodemy.data.api

import com.kltn.ecodemy.domain.models.zalo.ZaloOrderResponse
import okhttp3.FormBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ZalopayApi {
    @POST("create")
    suspend fun createOrder(
        @Header("Content-Type") contentType: String,
        @Body formBody: FormBody
    ): ZaloOrderResponse
}