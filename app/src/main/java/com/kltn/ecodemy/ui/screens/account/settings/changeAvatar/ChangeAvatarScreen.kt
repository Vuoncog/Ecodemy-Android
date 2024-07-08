package com.kltn.ecodemy.ui.screens.account.settings.changeAvatar

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.SnackBarStatus
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.domain.viewmodels.ChangeAvatarViewModel
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.screens.account.settings.SettingsTopBar
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChangeAvatarScreen(
    onBackClicked: () -> Unit,
    onCaptureClicked: () -> Unit,
    changeAvatarViewModel: ChangeAvatarViewModel,
) {
    val changeAvatarUiState = changeAvatarViewModel.changeAvatarUiState.collectAsState()
    val currentAvatar = changeAvatarUiState.value.currentAvatar
    val snackBarState = changeAvatarViewModel.statusBarState
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context = context)
        .data(changeAvatarUiState.value.bitmap ?: currentAvatar)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()
    val isUploading = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            SettingsTopBar(
                onBackClicked = onBackClicked,
                title = stringResource(id = R.string.change_avatar)
            )
        },
        bottomBar = {
            KocoButton(
                disable = isUploading.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(
                        horizontal = Constant.PADDING_SCREEN,
                        vertical = 20.dp
                    ),
                textContent = stringResource(id = R.string.change_avatar), icon = null,
                onClick = {
                    isUploading.value = true
                    changeAvatarViewModel.uploadAvatar(
                        onSuccess = {
                            changeAvatarViewModel.emitSnackBarStatus(
                                SnackBarStatus(
                                    isShow = true,
                                    message = context.getString(R.string.your_avatar_updated)
                                )
                            )
                            isUploading.value = false
                            onBackClicked()
                        },
                        onFailure = {
                            isUploading.value = false
                            changeAvatarViewModel.emitSnackBarStatus(
                                SnackBarStatus(
                                    isShow = true,
                                    message = context.getString(R.string.update_process_failed)
                                )
                            )
                        },
                        oldAvatar = {
                            changeAvatarViewModel.emitSnackBarStatus(
                                SnackBarStatus(
                                    isShow = true,
                                    message = context.getString(R.string.please_choose_new_avatar)
                                )
                            )
                        }
                    )
                })
        }
    ) {
        ChangeAvatarContent(
            paddingValues = it,
            imageRequest = imageRequest,
            onChooseClicked = { uri ->
                changeAvatarViewModel.assignPhotoAsUri(uri, context)
            },
            onCaptureClicked = onCaptureClicked,
        )
    }

    if (isUploading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary1)
        }
    }

    LaunchedEffect(key1 = snackBarState) {
        snackBarState.collectLatest {
            if (it.isShow) {
                Log.d("Snackbar", it.message)
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}