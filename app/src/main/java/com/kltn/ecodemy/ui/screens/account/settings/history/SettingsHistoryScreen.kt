package com.kltn.ecodemy.ui.screens.account.settings.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.SettingsHistoryViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.account.settings.SettingsTopBar
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary3

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsHistoryScreen(
    settingsHistoryViewModel: SettingsHistoryViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val settingsHistoryUiState =
        settingsHistoryViewModel.settingsHistoryUiState.collectAsState().value

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            SettingsTopBar(
                onBackClicked = onBackClicked,
                title = stringResource(id = R.string.payment_history)
            )
        }
    ) { paddingValues ->
        when (settingsHistoryUiState) {
            is RequestState.Success -> {
                SettingsHistoryContent(
                    listHistory = settingsHistoryUiState.data.listPaymentHistory,
                    paddingValues = paddingValues
                )
            }

            is RequestState.Idle -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    EcodemyText(
                        format = Nunito.Title1,
                        data = stringResource(R.string.you_have_not_owned_any_course),
                        color = Neutral2
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Primary3
                    )
                }
            }
        }
    }

}