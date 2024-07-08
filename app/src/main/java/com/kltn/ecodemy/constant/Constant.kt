package com.kltn.ecodemy.constant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object Constant {
    const val PREFERENCES_NAME = "ecodemy_preferences"
    const val PREFERENCES_LANGUAGE_KEY = "language_key"

    const val FIREBASE_DATABASE_URL = "Firebase KEY"
    const val FIREBASE_MESSAGE_URL = "FCM KEY"
    const val RECOMMENDER_SYSTEM_URL = "AZURE VM URL"
    const val NOTIFICATION_CHAT_CHANNEL = "Chat ID"

    const val SPLITTER = "<SPLIT>"
    const val START_DESTINATION = "start_destination"

    const val APP_ID = "application-0-bixar"
    const val BASE_URL = "MONGODB KEY"
    const val TEACHER_ID = "teacherId"
    const val OWNER_ID = "ownerId"
    const val TEACHER_ROLE = "Teacher"
    const val SEARCH_TEXT = "searchText"

    const val FILLED_STAR = "filled_star"
    const val HALF_STAR = "half_star"
    const val EMPTY_STAR = "empty_star"

    const val FULLNAME = "Full Name"
    const val EMAIL = "Email"
    const val PHONE = "Phone"
    const val CONTACTDATE = "Contact date"
    const val USER = "FullName"
    const val PASSWORD = "Password"
    const val REENTERPASSWORD = "Re-enter password"

    val HOME_AVATAR_SIZE = 40.dp
    private val BORDER_RADIUS_SHAPE = 12.dp
    val MINIMUM_INTERACTIVE_SIZE = 40.dp
//    val MAXIMUM_INTERACTIVE_SIZE = 56.dp
    val ICON_INTERACTIVE_SIZE = 48.dp
    val BORDER_SHAPE = RoundedCornerShape(BORDER_RADIUS_SHAPE)
    val ITEM_SPACE = Arrangement.spacedBy(8.dp)
    val PADDING_SCREEN = 16.dp
    val CARD_VERTICAL_PADDING = PaddingValues(top = 12.dp, bottom = 8.dp)

    const val ZALO_APP_ID = 2553
    const val MAC_KEY = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL"
    const val ZALO_URL = "https://sb-openapi.zalopay.vn/v2/"

    const val ZALO = "Zalo"
    const val MOMO = "Momo"

    const val PATTERN = "HH:mm dd/MM/yyyy"
    const val MESSAGE_PATTERN = "HH:mm"
    const val MESSAGE_DATE_PATTERN = "dd MMMM yyyy"
//    const val LOCALDATETIME_PATTERN = "uuuu-MM-dd'T'HH:mm:ss.SSS"
}