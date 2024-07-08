package com.kltn.ecodemy.data.impl

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.kltn.ecodemy.constant.Constant.APP_ID
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserInfo
import com.kltn.ecodemy.domain.repository.AuthenticationMongoDB
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.ext.profileAsBsonDocument
import javax.inject.Inject

class AuthenticationMongoDBImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
    private val firebaseDataProcess: FirebaseDataProcess,
) : AuthenticationMongoDB {
    private val app = App.create(APP_ID)
    private val user = mutableStateOf(app.currentUser)
    private val isLogged = mutableStateOf(false)
    private val userInfo = mutableStateOf(User())
    private val firebaseUser = FirebaseAuth.getInstance()

    override suspend fun loginByEmailPassword(email: String, password: String): Boolean {
        val credentials = Credentials.emailPassword(
            email = email,
            password = password
        )
        user.value = app.login(credentials)
        isLogged.value = user.value?.loggedIn ?: false
        firebaseUser.signInAnonymously()
        return isLogged.value
    }

    override suspend fun logout() {
        if (app.currentUser?.loggedIn == true) {
            app.currentUser?.logOut()
            firebaseUser.signOut()
            user.value = null
        } else {
            Log.d("Logout", "Failed")
        }
    }

    override fun isLogin(): Boolean {
        val user = user.value
        if (user != null) {
            isLogged.value = user.loggedIn
        } else {
            isLogged.value = false
        }
        return isLogged.value
    }

    override suspend fun getUser(
        onSuccessApi: (User) -> Unit,
    ) {
        onSuccessApi(userInfo.value)
    }

    override suspend fun refreshUser(
        coerce: Boolean
    ) {
        if (userInfo.value == User() || coerce) {
            Log.d(
                "Chay truong hop nao",
                (userInfo.value == User()).toString() + " " + coerce.toString()
            )
            getUserFromApi()
        }
    }

    private suspend fun getUserFromApi() {
        val email = app.currentUser?.profileAsBsonDocument()?.get("email")?.asString()?.value
        val ownerId = app.currentUser?.id
        if (email != null && ownerId != null) {
            try {
                val userResponse = ecodemyApi.getSelectedUser(
                    ownerId = ownerId,
                    email = email
                )
                userInfo.value = userResponse.first()
            } catch (_: Exception) {
                userInfo.value = User()
            }
        } else {
            userInfo.value = User()
        }
    }

    override suspend fun addNewUser(email: String, password: String, fullName: String): Boolean {
        val added = mutableStateOf(false)

        app.emailPasswordAuth.registerUser(
            email = email,
            password = password
        )

        val credentials = Credentials.emailPassword(
            email = email,
            password = password
        )
        val logged = app.login(credentials).loggedIn
        if (logged) {
            val user = app.currentUser!!
            val response = ecodemyApi.insertNewUser(
                user = User(
                    _id = null,
                    userInfo = UserInfo(
                        fullName = fullName,
                        email = email,
                    ),
                    ownerId = user.id,
                    role = Role.Guide
                )
            )
            added.value = response.insertedId.isNotBlank()
            if (added.value) {
                firebaseDataProcess.addNewAccount(
                    ownerId = user.id,
                    name = fullName,
                    avatar = ""
                )
            }
            user.logOut()
        }
        return logged.and(added.value)
    }

    override suspend fun resetPassword(oldPassword: String, newPassword: String): Boolean {
        val currentUser = app.currentUser
        if (currentUser == null) {
            Log.d("bngoc", "User not logged in")
            return false
        }
        val email = currentUser.profileAsBsonDocument()["email"]?.asString()?.value ?: return false
        var isOldPasswordCorrect = false
        try {
            isOldPasswordCorrect = loginByEmailPassword(email, oldPassword)
        } catch (e: Exception) {
            Log.d("bngoc", "Invalid old password")
        }
        if (!isOldPasswordCorrect) {
            Log.d("bngoc", "Old password is incorrect")
            return false
        }
        Log.d("bngoc", "Old password is correct")
        val result = app.emailPasswordAuth.callResetPasswordFunction(email, newPassword)
        Log.d("bngoc", "Password reset $result")
        return true
    }

    override suspend fun applyNewPassword(email: String, newPassword: String) =
        app.emailPasswordAuth.callResetPasswordFunction(email, newPassword)

}