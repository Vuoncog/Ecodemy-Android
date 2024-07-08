package com.kltn.ecodemy.ui.screens.authenication.login

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.EMAIL
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.constant.Constant.PASSWORD
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.EcodemyTextField
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Nunito

private val SPACE_BETWEEN = Arrangement.spacedBy(12.dp)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginTextField(
    emailValue: String,
    isEmailWrong: Boolean,
    onEmailValueChanged: (String) -> Unit,
    passwordValue: String,
    isPasswordWrong: Boolean,
    onPasswordValueChanged: (String) -> Unit,
    emailKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    passKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    onLoginClicked: () -> Unit,
    onForgetClicked: () -> Unit,
    onTrailingIconClicked: () -> Unit,
    errorText: String,
    @DrawableRes passwordTrailingIcon: Int,
) {
    Column(
        verticalArrangement = SPACE_BETWEEN,
        modifier = Modifier.padding(horizontal = PADDING_SCREEN)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (isEmailWrong.or(isPasswordWrong)) {
                EcodemyText(format = Nunito.Title1, data = errorText, color = Danger)
            }
            EcodemyTextField(
                leadingIcon = R.drawable.envelope,
                label = EMAIL,
                value = emailValue,
                isWrong = isEmailWrong,
                onValueChanged = {
                    onEmailValueChanged(it)
                },
                keyboardActions = emailKeyboard.first,
                keyboardOptions = emailKeyboard.second
            )
        }

        EcodemyTextField(
            leadingIcon = R.drawable.lock,
            label = PASSWORD,
            value = passwordValue,
            isWrong = isPasswordWrong,
            onValueChanged = {
                onPasswordValueChanged(it)
            },
            keyboardActions = passKeyboard.first,
            keyboardOptions = passKeyboard.second,
            trailingIcon = passwordTrailingIcon,
            onTrailingIconClicked = onTrailingIconClicked
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
//            EcodemyText(
//                format = Nunito.Subtitle1,
//                data = stringResource(id = R.string.login_remember_me),
//                color = Neutral1
//            )

            EcodemyText(
                format = Nunito.Subtitle1,
                data = stringResource(id = R.string.login_forget_password),
                color = Danger,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRippleEffect(true) {
                        onForgetClicked()
                    },
                textAlign = TextAlign.End
            )
        }

        KocoButton(
            modifier = Modifier.fillMaxWidth(),
            textContent = stringResource(id = R.string.login_title),
            icon = null,
            onClick = onLoginClicked
        )
    }
}