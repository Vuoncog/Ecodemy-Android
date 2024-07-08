package com.kltn.ecodemy.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.topBorder
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Neutral3

@Composable
fun PaymentBotBar(
    isPurchasing: Boolean,
    onPurchaseClicked: () -> Unit,
) {
    KocoButton(
        textContent = if (isPurchasing) stringResource(id = R.string.payment_purchasing) else stringResource(id = R.string.purchase_title),
        icon = null,
        onClick = onPurchaseClicked,
        disable = isPurchasing,
        modifier = Modifier
            .topBorder(
                strokeWidth = 1.dp,
                color = Neutral3
            )
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                vertical = 8.dp,
                horizontal = 32.dp
            )

    )
}