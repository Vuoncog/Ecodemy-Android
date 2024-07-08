package com.kltn.ecodemy.data.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.auth.oauth2.GoogleCredentials
import com.google.common.collect.Lists
import com.kltn.ecodemy.domain.repository.AccessToken
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class AccessTokenImpl @Inject constructor(
    private val firebaseDataProcess: FirebaseDataProcess,
) : AccessToken {
    private val accessToken = mutableStateOf("")
    private val firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging"

    override fun setAccessToken() {
        try {
            firebaseDataProcess.getJsonToken {
                val okHttpClient = OkHttpClient()
                val request = Request.Builder().url(it.toString()).build()
                val response = okHttpClient.newCall(request).execute()
                val jsonString = response.body?.string().toString()
                val stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))
                val credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList(firebaseMessagingScope))
                credentials.refresh()
                accessToken.value = credentials.accessToken.tokenValue
            }
        } catch (e: Exception) {
            Log.d("AccessTokenError", e.toString())
        }
    }

    override fun getAccessToken(): String {
        return accessToken.value
    }
}