package com.kltn.ecodemy.ui.screens.authenication.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.authenication.components.AuthButton
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

@Composable
fun LoginGoogle(
    onClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = PADDING_SCREEN
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EcodemyText(
            format = Nunito.Title1,
            data = stringResource(id = R.string.login_login_with),
            color = Neutral3
        )

        AuthButton(
            textContent = "Google",
            icon = R.drawable.google,
            onClick = onClicked,
            borderColor = Primary1,
        )
    }
}