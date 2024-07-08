package com.kltn.ecodemy.data.api

import com.kltn.ecodemy.domain.models.fcm.FcmResponse
import com.kltn.ecodemy.domain.models.fcm.FirebaseMessage
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FirebaseCloudMessagingApi {

    @POST("./messages:send")
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body firebaseMessage: FirebaseMessage,
    ): FcmResponse
}