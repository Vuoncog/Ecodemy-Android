package com.kltn.ecodemy.ui.screens.authenication.forget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.viewmodels.ForgetViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.EcodemyTextFieldFullCase
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import kotlinx.coroutines.FlowPreview

@FlowPreview
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewPasswordScreen(
    forgetViewModel: ForgetViewModel,
    onChangeClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val forgetUiState = forgetViewModel.forgetUiState.collectAsState().value
    val focusManager = LocalFocusManager.current
    val hidePassword = remember {
        mutableStateOf(true)
    }
    val hideReEnterPassword = remember {
        mutableStateOf(true)
    }

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Constant.PADDING_SCREEN)
                .imePadding()
                .padding(
                    top = 16.dp,
                    bottom = 64.dp
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .clickableWithoutRippleEffect(true) {
                        forgetViewModel.clearWhenBackToForget()
                        onBackClicked()
                    }
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                EcodemyText(format = Nunito.Subtitle1, data = "Back", color = Neutral1)
            }

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.verification),
                contentDescription = null,
                modifier = Modifier
                    .padding(32.dp)
                    .size(144.dp)
            )
            EcodemyText(
                format = Nunito.Subtitle1,
                data = stringResource(R.string.find_account_content),
                color = Neutral2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            EcodemyTextFieldFullCase(
                leadingIcon = R.drawable.lock,
                label = Constant.PASSWORD,
                value = forgetUiState.password,
                onValueChanged = forgetViewModel::onPasswordChanged,
                errorMessage = forgetUiState.passwordError,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isWrong = forgetUiState.passwordError.isNotBlank(),
                trailingIcon = if (hidePassword.value) R.drawable.hide else R.drawable.show_alt,
                onTrailingIconClicked = {
                    hidePassword.value = !hidePassword.value
                }
            )

            EcodemyTextFieldFullCase(
                leadingIcon = R.drawable.lock,
                label = Constant.REENTERPASSWORD,
                value = forgetUiState.reEnterPassword,
                onValueChanged = forgetViewModel::onReEnterPasswordChanged,
                errorMessage = forgetUiState.reEnterPasswordError,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                isWrong = forgetUiState.reEnterPasswordError.isNotBlank(),
                trailingIcon = if (hideReEnterPassword.value) R.drawable.hide else R.drawable.show_alt,
                onTrailingIconClicked = {
                    hideReEnterPassword.value = !hideReEnterPassword.value
                }
            )

            KocoButton(
                textContent = stringResource(R.string.change_password), icon = null,
                modifier = Modifier.fillMaxWidth(),
                disable = forgetUiState.isLoading
            ) {
                forgetViewModel.applyNewPassword {
                    onChangeClicked()
                }
            }
        }
    }
}