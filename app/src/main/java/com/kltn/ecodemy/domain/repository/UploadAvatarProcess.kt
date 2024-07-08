package com.kltn.ecodemy.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.CoroutineScope

interface UploadAvatarProcess {
    fun uploadAvatar(
        bitmap: Bitmap,
        ownerId: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        viewModelScope: CoroutineScope
    )
}