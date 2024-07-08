package com.kltn.ecodemy.ui.screens.authenication.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.viewmodels.LoginViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.authenication.components.AuthBackButton
import com.kltn.ecodemy.ui.screens.authenication.components.AuthButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    onBackClicked: () -> Unit,
    onForgetClicked: () -> Unit,
    onSignupClicked: () -> Unit,
    onLoginClicked: (Boolean) -> Unit,
    loginViewModel: LoginViewModel,
) {
    val state = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val loginUiState = loginViewModel.loginUiState.collectAsState().value
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(paddingValues)
            .verticalScroll(
                state = state
            )
            .padding(bottom = 64.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_banner),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AuthBackButton(
                    onClick = onBackClicked,
                )
                AuthButton(
                    textContent = stringResource(id = R.string.signup_title),
                    icon = null,
                    onClick = onSignupClicked,
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = PADDING_SCREEN)
        ) {
            EcodemyText(
                format = Nunito.Heading2,
                data = stringResource(id = R.string.login_welcome),
                color = Neutral1
            )
            Spacer(modifier = Modifier.height(4.dp))
            EcodemyText(
                format = Nunito.Subtitle1,
                data = stringResource(id = R.string.login_welcome_2),
                color = Neutral2
            )
        }

        LoginTextField(
            emailValue = loginUiState.email,
            isEmailWrong = loginUiState.isEmailWrong,
            onEmailValueChanged = loginViewModel::updateEmail,
            passwordValue = loginUiState.password,
            isPasswordWrong = loginUiState.isPasswordWrong,
            passwordTrailingIcon = if (loginUiState.hidePassword) R.drawable.hide else R.drawable.show_alt,
            onPasswordValueChanged = loginViewModel::updatePassword,
            emailKeyboard = Pair(
                KeyboardActions(
                    onNext = {
                        scope.launch {
                            state.animateScrollTo(
                                state.maxValue,
                                animationSpec = tween(300, 0, LinearOutSlowInEasing)
                            )
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    },
                ),
                KeyboardOptions(imeAction = ImeAction.Next)
            ),
            passKeyboard = Pair(
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                ),
                KeyboardOptions(imeAction = ImeAction.Done)
            ),
            onLoginClicked = {
                keyboard?.hide()
                loginViewModel.loginAccount(
                    onSuccess = onLoginClicked
                )
            },
            onTrailingIconClicked = loginViewModel::updateHidePassword,
            errorText = loginUiState.errorMessage,
            onForgetClicked = onForgetClicked
        )

//        LoginGoogle(
//            onClicked = {
//                loginViewModel.ce()
//            }
//        )

    }
}