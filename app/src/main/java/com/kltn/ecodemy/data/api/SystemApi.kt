package com.kltn.ecodemy.data.api

import com.google.gson.JsonElement
import com.kltn.ecodemy.domain.models.RecommenderResponse
import com.kltn.ecodemy.domain.models.otp.OTPRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SystemApi {
    @GET("recommendations")
    suspend fun getAll(): List<RecommenderResponse>

    @POST("otp/signup")
    suspend fun sendOTP(
        @Body otpRequest: OTPRequest
    ): JsonElement

    @POST("otp/resetpass")
    suspend fun sendRegistrationOTP(
        @Body otpRequest: OTPRequest
    ): JsonElement

    @GET("recommendationslist/user/{userId}")
    suspend fun getInCommonCourse(
        @Path("userId") userId: String,
    ): List<String>
}