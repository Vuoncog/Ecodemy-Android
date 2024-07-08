package com.kltn.ecodemy.ui.screens.authenication.signup

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.PASSWORD
import com.kltn.ecodemy.constant.Constant.REENTERPASSWORD
import com.kltn.ecodemy.domain.viewmodels.SignupViewModel
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.EcodemyTextFieldFullCase

private val SPACE_BETWEEN = Arrangement.spacedBy(12.dp)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupTextField(
    signupViewModel: SignupViewModel,
    fullNameKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    emailKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    passwordKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    reEnterPasswordKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    onSignupClicked: () -> Unit,
) {
    val signupUiState = signupViewModel.signupUiState.collectAsState().value
    Column(
        verticalArrangement = SPACE_BETWEEN,
        modifier = Modifier.padding(horizontal = Constant.PADDING_SCREEN)
    ) {
        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.user,
            label = stringResource(id = R.string.signup_full_name),
            keyboardActions = fullNameKeyboard.first,
            keyboardOptions = fullNameKeyboard.second,
            value = signupUiState.fullName,
            onValueChanged = signupViewModel::updateFullName,
            isWrong = !signupUiState.isFullNameValid,
            errorMessage = signupUiState.errorMessage[Constant.USER] ?: "",
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        Log.d("HoverTextField", "On Hovering")
                    }
                }
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.envelope,
            label = Constant.EMAIL,
            keyboardActions = emailKeyboard.first,
            keyboardOptions = emailKeyboard.second,
            value = signupUiState.email,
            onValueChanged = signupViewModel::updateEmail,
            isWrong = !signupUiState.isEmailValid,
            errorMessage = signupUiState.errorMessage[Constant.EMAIL] ?: "",
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.lock,
            label = PASSWORD,
            keyboardActions = passwordKeyboard.first,
            keyboardOptions = passwordKeyboard.second,
            value = signupUiState.password,
            onValueChanged = signupViewModel::updatePassword,
            trailingIcon = if (signupUiState.hidePassword) R.drawable.hide else R.drawable.show_alt,
            onTrailingIconClicked = signupViewModel::updateHidePassword,
            isWrong = !signupUiState.isPasswordValid,
            errorMessage = signupUiState.errorMessage[PASSWORD] ?: "",
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.lock,
            label = REENTERPASSWORD,
            keyboardActions = reEnterPasswordKeyboard.first,
            keyboardOptions = reEnterPasswordKeyboard.second,
            value = signupUiState.reEnterPassword,
            onValueChanged = signupViewModel::updateReEnterPassword,
            trailingIcon = if (signupUiState.hideReEnterPassword) R.drawable.hide else R.drawable.show_alt,
            onTrailingIconClicked = signupViewModel::updateHideReEnterPassword,
            isWrong = !signupUiState.isReEnterPasswordValid,
            errorMessage = signupUiState.errorMessage[REENTERPASSWORD] ?: "",
        )

        KocoButton(
            modifier = Modifier.fillMaxWidth(),
            textContent = stringResource(id = R.string.signup_title),
            icon = null,
            disable = signupUiState.isLoading,
            onClick = {
                signupViewModel.receiveOTP(
                    onSucceed = onSignupClicked
                )
            }
        )
    }
}