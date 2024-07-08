package com.kltn.ecodemy.domain.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.AvatarRepository
import com.kltn.ecodemy.domain.models.SnackBarStatus
import com.kltn.ecodemy.domain.models.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangeAvatarViewModel @Inject constructor(
    private val avatarRepository: AvatarRepository,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val _changeAvatarUiState = MutableStateFlow(ChangeAvatarUiState())
    val changeAvatarUiState = _changeAvatarUiState.asStateFlow()

    private val _statusBarState = MutableSharedFlow<SnackBarStatus>()
    val statusBarState = _statusBarState.asSharedFlow()


    init {
        getUser()
    }

    fun assignPhoto(bitmap: Bitmap?) {
        _changeAvatarUiState.value = _changeAvatarUiState.value.copy(
            bitmap = bitmap
        )
    }

    fun assignPhotoAsUri(uri: Uri?, context: Context) {
        if (uri != null) {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
            assignPhoto(bitmap)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            authenticationRepository.getUser {
                _changeAvatarUiState.value = _changeAvatarUiState.value.copy(
                    user = it,
                    currentAvatar = it.userInfo.avatar
                )
            }
        }
    }

    fun uploadAvatar(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        oldAvatar: () -> Unit,
    ) {
        val bitmap = _changeAvatarUiState.value.bitmap
        val ownerId = _changeAvatarUiState.value.user.ownerId
        if (bitmap != null) {
            avatarRepository.uploadAvatar(
                bitmap = bitmap,
                ownerId = ownerId,
                onSuccess = onSuccess,
                onFailure = onFailure,
                viewModelScope = viewModelScope
            )
        } else {
            oldAvatar()
        }
    }

    fun emitSnackBarStatus(snackBarStatus: SnackBarStatus) {
        viewModelScope.launch {
            _statusBarState.emit(snackBarStatus)
        }
    }
}

data class ChangeAvatarUiState(
    val bitmap: Bitmap? = null,
    val user: User = User(),
    val currentAvatar: String = "",
)