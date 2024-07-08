package com.kltn.ecodemy.ui.screens.authenication.verification

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun OTPSuccess(
    modifier: Modifier = Modifier,
    onSuccessClicked: () -> Unit,
    arg: Boolean,
) {
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = PADDING_SCREEN)
                .padding(
                    top = 56.dp,
                    bottom = 64.dp
                ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.success_verification),
                contentDescription = null,
                modifier = Modifier
                    .padding(32.dp)
                    .size(144.dp)
            )
            Column {
                EcodemyText(
                    format = Nunito.Subtitle1,
                    data = if (arg) stringResource(R.string.verified_account)
                     else stringResource(R.string.log_in_account_with_new_password),
                    color = Neutral2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                EcodemyText(
                    format = Nunito.Heading2,
                    data = if (arg) stringResource(R.string.sign_up_successfully)
                    else stringResource(R.string.password_changed),
                    color = Neutral1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }


            KocoButton(
                textContent = "Back to login", icon = null,
                modifier = Modifier.fillMaxWidth()
            ) {
                onSuccessClicked()
            }
        }
    }
}