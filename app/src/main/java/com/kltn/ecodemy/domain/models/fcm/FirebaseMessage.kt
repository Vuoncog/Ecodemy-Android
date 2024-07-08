package com.kltn.ecodemy.domain.models.fcm

data class FirebaseMessage(
    val message: FcmMessage,
)

data class FcmMessage(
    val token: String,
    val notification: FcmNotification,
    val data: FcmData? = null,
)

data class FcmNotification(
    val body: String,
    val title: String,
)

data class FcmData(
    val image: String,
)
