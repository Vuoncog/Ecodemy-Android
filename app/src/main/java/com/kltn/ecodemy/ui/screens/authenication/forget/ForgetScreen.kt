package com.kltn.ecodemy.ui.screens.authenication.forget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.kltn.ecodemy.constant.Constant.EMAIL
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.viewmodels.ForgetViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.EcodemyTextFieldFullCase
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.FlowPreview

@FlowPreview
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForgetScreen(
    forgetViewModel: ForgetViewModel,
    onFindClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val forgetUiState = forgetViewModel.forgetUiState.collectAsState().value
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Constant.PADDING_SCREEN)
                .padding(
                    top = 16.dp,
                )
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .clickableWithoutRippleEffect(true) {
                        forgetViewModel.clearWhenBackToLogin()
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

            EcodemyTextFieldFullCase(
                leadingIcon = R.drawable.user,
                label = EMAIL,
                value = forgetUiState.email,
                onValueChanged = forgetViewModel::onEmailChanged,
                errorMessage = forgetUiState.emailError,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                isWrong = forgetUiState.emailError.isNotEmpty()
            )

            KocoButton(
                textContent = stringResource(R.string.find_account), icon = null,
                modifier = Modifier.fillMaxWidth(),
                disable = forgetUiState.isLoading
            ) {
                forgetViewModel.receiveOTP {
                    onFindClicked()
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