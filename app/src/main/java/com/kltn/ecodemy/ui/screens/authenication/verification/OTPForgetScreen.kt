package com.kltn.ecodemy.ui.screens.authenication.verification

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.viewmodels.ForgetViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun OTPForgetScreen(
    modifier: Modifier = Modifier,
    forgetViewModel: ForgetViewModel,
    onVerifyClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val forgetUiState = forgetViewModel.forgetUiState.collectAsState().value
    val email = forgetUiState.email

    val first = remember { mutableStateOf("") }
    val second = remember { mutableStateOf("") }
    val third = remember { mutableStateOf("") }
    val fourth = remember { mutableStateOf("") }
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .imePadding()
                .padding(paddingValues)
                .padding(horizontal = Constant.PADDING_SCREEN)
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
            Column {
                EcodemyText(
                    format = Nunito.Subtitle1,
                    data = stringResource(R.string.we_have_sent_a_verification_code_to_email),
                    color = Neutral2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                EcodemyText(
                    format = Nunito.Heading2,
                    data = email,
                    color = Neutral1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                EcodemyText(
                    format = Nunito.Subtitle1,
                    data = forgetUiState.otpError,
                    color = Danger,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            OTP(
                first = first.value,
                second = second.value,
                third = third.value,
                fourth = fourth.value,
                onFirstChanged = { first.value = it },
                onSecondChanged = { second.value = it },
                onThirdChanged = { third.value = it },
                onFourthChanged = { fourth.value = it }
            )

            EcodemyText(
                format = Nunito.Subtitle3,
                data = stringResource(R.string.otp_is_available_in_1_minute),
                color = Neutral2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            KocoButton(
                textContent = stringResource(R.string.verify), icon = null,
                modifier = Modifier.fillMaxWidth(),
                disable = forgetUiState.isLoading
            ) {
                val otp = first.value + second.value +
                        third.value + fourth.value
                forgetViewModel.verify(
                    onSucceed = {
                        onVerifyClicked()
                    }, inputOTP = otp
                )
            }

            if (forgetUiState.resend) {
                KocoButton(
                    textContent = stringResource(R.string.resend_otp), icon = null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    forgetViewModel.resendOTP()
                }
            }
        }
    }
    if (forgetUiState.isLoading) {
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