package com.kltn.ecodemy.ui.screens.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.formatToMessageDateString
import com.kltn.ecodemy.constant.formatToMessageTimeString
import com.kltn.ecodemy.constant.toLocalDateTime
import com.kltn.ecodemy.domain.viewmodels.NotificationViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.account.settings.SettingsTopBar
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationScreen(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val notificationMap = notificationViewModel.notificationList.collectAsState()
    Scaffold(
        topBar = {
            SettingsTopBar(
                onBackClicked = onBackClicked,
                title = stringResource(id = R.string.notification)
            )
        },
        containerColor = BackgroundColor
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            notificationMap.value.forEach { (date, notification) ->
                item {
                    EcodemyText(
                        format = Nunito.Subtitle2,
                        data = date.formatToMessageDateString(),
                        color = Neutral2,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    )
                }
                items(notification) { notificationMapper ->
                    NotificationItem(
                        image = notificationMapper.image,
                        title = notificationMapper.title,
                        content = notificationMapper.content,
                        time = notificationMapper.time.toLocalDateTime().formatToMessageTimeString()
                    )
                    if (notificationMapper != notification.last()) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Neutral4,
                            modifier = Modifier.padding(horizontal = Constant.PADDING_SCREEN)
                        )
                    }
                }
            }
        }
    }
}