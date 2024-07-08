package com.kltn.ecodemy.ui.screens.account.settings.resetPassword

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.PASSWORD
import com.kltn.ecodemy.constant.Constant.REENTERPASSWORD
import com.kltn.ecodemy.constant.authBackground
import com.kltn.ecodemy.constant.moveDown
import com.kltn.ecodemy.domain.viewmodels.AccountViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.EcodemyTextFieldFullCase
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.EndLinear
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.StartLinear

private val SPACE_BETWEEN = Arrangement.spacedBy(16.dp)

@Composable
fun ResetPassword(
    accountViewModel: AccountViewModel,
    onBackClicked: () -> Unit,
    onChangeClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        content = { paddingValues ->
            ResetPasswordContent(
                paddingValues = paddingValues,
                onBackClicked = onBackClicked,
                onChangeClicked = onChangeClicked,
                accountViewModel = accountViewModel
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResetPasswordContent(
    paddingValues: PaddingValues,
    onBackClicked: () -> Unit,
    onChangeClicked: () -> Unit,
    accountViewModel: AccountViewModel
) {
    val state = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(paddingValues)
            .authBackground()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .verticalScroll(state),
    ) {
        ResetPasswordTopBar(
            onBackClicked = onBackClicked
        )
        Spacer(modifier = Modifier.size(20.dp))
        ResetPasswordTextField(
            accountViewModel = accountViewModel,
            oldPasswordKeyboard = moveDown(state, scope, focusManager),
            newPasswordKeyboard = moveDown(state, scope, focusManager),
            reEnterNewPasswordKeyboard = Pair(
                KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                KeyboardOptions(imeAction = ImeAction.Done)
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        KocoButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Constant.PADDING_SCREEN),
            textContent = "Change Password",
            icon = null,
//            onClick = {
//                accountViewModel.resetPassword()
//            }
            onClick = onChangeClicked
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResetPasswordTextField(
    accountViewModel: AccountViewModel,
    oldPasswordKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    newPasswordKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    reEnterNewPasswordKeyboard: Pair<KeyboardActions, KeyboardOptions>,
){
    val accountUiState = accountViewModel.accountUiState.collectAsState().value
    Column(
        verticalArrangement = SPACE_BETWEEN,
        modifier = Modifier.padding(
            horizontal = Constant.PADDING_SCREEN
        )
    ) {
        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.lock,
            label = "Old Password",
            keyboardActions = oldPasswordKeyboard.first,
            keyboardOptions = oldPasswordKeyboard.second,
            value = accountUiState.oldPassword,
            onValueChanged = accountViewModel::updateOldPassword,
            isWrong = !accountUiState.isChangePasswordComplete,
            errorMessage = "Mật khẩu không đúng"
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.lock,
            label = "New password",
            keyboardActions = newPasswordKeyboard.first,
            keyboardOptions = newPasswordKeyboard.second,
            value = accountUiState.newPassword,
            onValueChanged = accountViewModel::updateNewPassword,
            isWrong = !accountUiState.isNewPasswordValid,
            errorMessage = accountUiState.errorMessage[PASSWORD] ?: "",
        )
        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.lock,
            label = "Re-enter new password",
            keyboardActions = reEnterNewPasswordKeyboard.first,
            keyboardOptions = reEnterNewPasswordKeyboard.second,
            value = accountUiState.reEnterNewPassword,
            onValueChanged = accountViewModel::updateReEnterNewPassword,
            isWrong = !accountUiState.isReEnterNewPasswordValid,
            errorMessage = accountUiState.errorMessage[REENTERPASSWORD] ?: "",
        )
    }
}

@Composable
fun ResetPasswordTopBar(
    onBackClicked: () -> Unit,
    height: Dp = 56.dp,
) {
    Row(
        modifier = Modifier
            .height(height)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(StartLinear, EndLinear),
                    start = Offset.Infinite,
                    end = Offset.Zero
                )
            )
            .padding(
                end = 4.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                contentDescription = "Back",
                tint = Neutral1
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            EcodemyText(
                format = Nunito.Heading2,
                data = "Reset Password",
                color = Neutral1,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
    }
}