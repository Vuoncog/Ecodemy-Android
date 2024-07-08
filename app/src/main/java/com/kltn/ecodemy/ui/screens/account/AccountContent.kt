package com.kltn.ecodemy.ui.screens.account

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.OWNER_ID
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.viewmodels.AccountViewModel
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import io.realm.kotlin.mongodb.App

private val ACCOUNT_HEADER_BACKGROUND_HEIGHT = 56.dp
private val ACCOUNT_HORIZONTAL_PADDING = 16.dp
private val SETTING_BOX_TOP_PADDING = 12.dp
private val SETTING_BOX_BOTTOM_PADDING = 8.dp
private val ACCOUNT_ITEMS_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)

@Composable
fun AccountContent(
    paddingValues: PaddingValues,
    viewModel: AccountViewModel = hiltViewModel(),
    onSettingClicked: (String) -> Unit,
    onLogoutClicked: () -> Unit,
    onLoginClicked: () -> Unit,
) {
    val accountUiState = viewModel.accountUiState.collectAsState().value
    val userInfo = accountUiState.user
    val isLogin = accountUiState.isLogged

    LaunchedEffect(key1 = accountUiState) {
        viewModel.isLogged()
        Log.d("LogCheck", isLogin.toString())
        val app = App.create(Constant.APP_ID)
        Log.d("LogCheck2", app.currentUser?.id ?: "Log out")
    }

    if (isLogin) {
        AccountLogin(
            paddingValues = paddingValues,
            user = userInfo,
            onSettingClicked = onSettingClicked,
            onLogoutClicked = onLogoutClicked,
            systemSettingsList = accountUiState.systemSettingsList,
            accountSettingsList = accountUiState.accountSettingsList
        )
    } else {
        AccountNonLogin(
            paddingValues = paddingValues,
            onSettingClicked = onSettingClicked,
            onLoginClicked = onLoginClicked
        )
    }
}

@Composable
fun AccountLogin(
    paddingValues: PaddingValues,
    user: User,
    onSettingClicked: (String) -> Unit,
    onLogoutClicked: () -> Unit,
    systemSettingsList: List<Route.Setting.SettingItem>,
    accountSettingsList: List<Route.Setting.SettingItem>,
) {
    KocoScreen(
        headerBackgroundHeight = ACCOUNT_HEADER_BACKGROUND_HEIGHT,
        headerBackgroundShape = RoundedCornerShape(0)
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = SETTING_BOX_BOTTOM_PADDING)
        ) {
            Row(
                modifier = Modifier
                    .height(ACCOUNT_HEADER_BACKGROUND_HEIGHT)
                    .padding(horizontal = PADDING_SCREEN),
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
                usrName = user.userInfo.fullName,
                usrGmail = user.userInfo.email,
                role = user.role.name,
                avatar = user.userInfo.avatar
            )
            //LazyColumn
            Column(
                verticalArrangement = ACCOUNT_ITEMS_SPACE_BETWEEN,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                AccountSettingsCard(
                    title = stringResource(R.string.general),
                    onSettingClicked = onSettingClicked,
                    listSettings = systemSettingsList
                )
                AccountSettingsCard(
                    title = stringResource(R.string.account),
                    onSettingClicked = onSettingClicked,
                    listSettings = accountSettingsList
                )
            }
            AccountButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color.White),
                textContent = stringResource(id = R.string.account_logout),
                onClick = onLogoutClicked
            )
        }
    }
}

@Composable
fun AccountSettingsCard(
    title: String,
    onSettingClicked: (String) -> Unit,
    listSettings: List<Route.Setting.SettingItem>,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                top = SETTING_BOX_TOP_PADDING,
                bottom = SETTING_BOX_BOTTOM_PADDING,
            )
            .padding(
                horizontal = ACCOUNT_HORIZONTAL_PADDING
            ),
    ) {
        EcodemyText(
            format = Nunito.Subtitle1,
            data = title,
            color = Neutral3,
        )
        listSettings.forEach {
            SettingButton(
                textContent = stringResource(id = it.title),
                subIcon = null,
                onClick = {
                    onSettingClicked(it.settingRoute.addArgument(arg = it.args, type = OWNER_ID))
                }
            )
        }
    }
}