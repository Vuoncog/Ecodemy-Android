package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.data.api.SystemApi
import com.kltn.ecodemy.domain.models.otp.OTPRequest
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.repository.AuthenticationMongoDB
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authenticationMongoDB: AuthenticationMongoDB,
    private val systemApi: SystemApi,
) {
    suspend fun loginByEmailPassword(email: String, password: String) =
        authenticationMongoDB.loginByEmailPassword(email, password)

    suspend fun logout() = authenticationMongoDB.logout()

    fun isLogged() = authenticationMongoDB.isLogin()
    suspend fun getUser(
        onSuccessApi: (User) -> Unit,
    ) = authenticationMongoDB.getUser(onSuccessApi = onSuccessApi)

    suspend fun refreshUser(
        coerce: Boolean = false
    ) = authenticationMongoDB.refreshUser(coerce)

    suspend fun addNewUser(
        email: String,
        password: String,
        fullName: String,
    ): Boolean = authenticationMongoDB.addNewUser(email, password, fullName)

    suspend fun resetPassword(
        oldPassword: String,
        newPassword: String,
    ): Boolean = authenticationMongoDB.resetPassword(oldPassword, newPassword)

    suspend fun otpRequest(email: String, otp: String) = systemApi.sendOTP(OTPRequest(email,otp))
    suspend fun otpResetPassRequest(email: String, otp: String) = systemApi.sendRegistrationOTP(OTPRequest(email,otp))
    suspend fun applyNewPassword(
        email: String,
        newPassword: String,
    ) = authenticationMongoDB.applyNewPassword(email, newPassword)
}