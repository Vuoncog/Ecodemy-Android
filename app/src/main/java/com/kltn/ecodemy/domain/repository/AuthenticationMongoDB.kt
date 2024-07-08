package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.user.User

interface AuthenticationMongoDB {
    suspend fun loginByEmailPassword(email: String, password: String): Boolean
    suspend fun logout()
    fun isLogin(): Boolean
    suspend fun getUser(
        onSuccessApi: (User) -> Unit
    )

    suspend fun refreshUser(coerce: Boolean)

    suspend fun addNewUser(
        email: String,
        password: String,
        fullName: String,
    ): Boolean

    suspend fun resetPassword(
        oldPassword: String,
        newPassword: String,
    ): Boolean

    suspend fun applyNewPassword(email: String, newPassword: String)
}