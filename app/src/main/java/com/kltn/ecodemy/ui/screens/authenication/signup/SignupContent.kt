package com.kltn.ecodemy.ui.screens.authenication.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.authBackground
import com.kltn.ecodemy.constant.moveDown
import com.kltn.ecodemy.domain.viewmodels.SignupViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.authenication.components.AuthBackButton
import com.kltn.ecodemy.ui.screens.authenication.components.AuthButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupContent(
    paddingValues: PaddingValues,
    onBackClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    signupViewModel: SignupViewModel,
    onSignupClicked: () -> Unit,
) {
    val state = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val signupUiState = signupViewModel.signupUiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(paddingValues)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .verticalScroll(state)
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
                contentScale = ContentScale.FillWidth
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AuthBackButton(
                    onClick = {
                        signupViewModel.updateFullName("")
                        signupViewModel.updateEmail("")
                        signupViewModel.updatePassword("")
                        signupViewModel.updateReEnterPassword("")
                        onBackClicked()
                    },
                )
                AuthButton(
                    textContent = stringResource(id = R.string.login_title),
                    icon = null,
                    onClick = onLoginClicked,
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = Constant.PADDING_SCREEN)
        ) {
            EcodemyText(
                format = Nunito.Heading2,
                data = stringResource(id = R.string.signup_become_a_student),
                color = Neutral1
            )
            Spacer(modifier = Modifier.height(4.dp))
            EcodemyText(
                format = Nunito.Subtitle1,
                data = stringResource(id = R.string.signup_credentials),
                color = Neutral2
            )
        }

        SignupTextField(
            signupViewModel = signupViewModel,
            fullNameKeyboard = moveDown(state, scope, focusManager),
            emailKeyboard = moveDown(state, scope, focusManager),
            passwordKeyboard = moveDown(state, scope, focusManager),
            reEnterPasswordKeyboard = Pair(
                KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                KeyboardOptions(imeAction = ImeAction.Done)
            ),
            onSignupClicked = onSignupClicked
        )
    }

    if (signupUiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary1)
        }
    }
}

