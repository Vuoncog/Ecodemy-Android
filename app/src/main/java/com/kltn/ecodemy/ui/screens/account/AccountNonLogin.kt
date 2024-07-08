package com.kltn.ecodemy.ui.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.PrimaryPressed

private val ACCOUNT_HEADER_BACKGROUND_HEIGHT = 56.dp

@Composable
fun AccountNonLogin(
    paddingValues: PaddingValues,
    onSettingClicked: (String) -> Unit,
    onLoginClicked: () -> Unit,
) {
    KocoScreen(
        headerBackgroundHeight = ACCOUNT_HEADER_BACKGROUND_HEIGHT,
        headerBackgroundShape = RoundedCornerShape(0)
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .height(ACCOUNT_HEADER_BACKGROUND_HEIGHT)
                        .padding(horizontal = Constant.PADDING_SCREEN),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    EcodemyText(
                        format = Nunito.Heading2,
                        data = stringResource(id = R.string.route_account),
                        color = Neutral1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
                AccountInfo(
                    usrName = "Ecodemy user",
                    usrGmail = null,
                    role = "Guide",
                )
            }
            AccountButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                textContent = stringResource(id = R.string.login_title),
                onClick = onLoginClicked,
                colorPressed = PrimaryPressed,
                colorDisplayed = Primary1,
            )

            AccountSettingsCard(
                title = stringResource(id = R.string.general),
                onSettingClicked = onSettingClicked,
                listSettings = listOf(
                    Route.Setting.SettingItem.Language,
                )
            )
        }
    }
}