package com.kltn.ecodemy.ui.screens.account.settings.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.constant.formatToString
import com.kltn.ecodemy.constant.toLocalDateTime
import com.kltn.ecodemy.domain.models.payment.PaymentHistory
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral4

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsHistoryContent(
    listHistory: List<PaymentHistory>,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(paddingValues)
    ) {
        itemsIndexed(listHistory) { index, paymentHistory ->
            SettingsHistoryItem(
                paymentDate = paymentHistory.date.toLocalDateTime().formatToString(),
                course = paymentHistory.courseData,
                teacherData = paymentHistory.teacherData
            )
            if (index != listHistory.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Neutral4,
                    modifier = Modifier.padding(horizontal = PADDING_SCREEN)
                )
            }
        }
    }
}