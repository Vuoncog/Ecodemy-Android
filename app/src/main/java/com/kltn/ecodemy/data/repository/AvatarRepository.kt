package com.kltn.ecodemy.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.kltn.ecodemy.domain.repository.UploadAvatarProcess
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AvatarRepository @Inject constructor(
    private val uploadAvatarProcess: UploadAvatarProcess
) {
    fun uploadAvatar(
        bitmap: Bitmap,
        ownerId: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        viewModelScope: CoroutineScope,
    ) = uploadAvatarProcess.uploadAvatar(
        bitmap = bitmap,
        ownerId = ownerId,
        onSuccess = onSuccess,
        onFailure = onFailure,
        viewModelScope = viewModelScope
    )
}