package com.kltn.ecodemy.data.impl

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import com.kltn.ecodemy.domain.repository.UploadAvatarProcess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UploadAvatarProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi,
    private val firebaseDataProcess: FirebaseDataProcess
) : UploadAvatarProcess {

    override fun uploadAvatar(
        bitmap: Bitmap,
        ownerId: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        viewModelScope: CoroutineScope
    ) {
        firebaseDataProcess.uploadImage(
            bitmap = bitmap,
            ownerId = ownerId,
            onFailure = {},
            onSuccess = {
                viewModelScope.launch {
                    val response = ecodemyApi.updateUserAvatar(
                        ownerId = ownerId,
                        avtUrl = it.toString()
                    )
                    if (response.matchedCount == 1) {
                        onSuccess()
                    } else {
                        onFailure()
                    }
                }
            }
        )
    }

}