package com.kltn.ecodemy.ui.screens.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.models.payment.PaymentStatus
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

private val IMAGE_SIZE = 72.dp

@Composable
fun PaymentDialog(
    openDialog: MutableState<Boolean>,
    paymentStatus: PaymentStatus,
    onClicked: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        onDismissRequest = {
            openDialog.value = !openDialog.value
        }
    ) {
        when (paymentStatus) {
            is PaymentStatus.SUCCESSFUL -> {
                PaymentDialogSuccess(
                    courseTitle = paymentStatus.title,
                    onClicked = onClicked
                )
            }

            is PaymentStatus.CANCELED -> {
                PaymentDialogCanceled(
                    onClicked = onClicked
                )
            }

            else -> {
                PaymentDialogError(
                    onClicked = onClicked
                )
            }
        }
    }
}

@Composable
fun PaymentDialogSuccess(
    courseTitle: String,
    onClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PADDING_SCREEN)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            )
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.successful),
            modifier = Modifier.size(IMAGE_SIZE),
            contentDescription = "Successful"
        )
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.payment_succeed),
            color = Neutral1
        )
        EcodemyText(
            format = Nunito.Subtitle1,
            data = stringResource(id = R.string.payment_succeed_2) + "\n$courseTitle",
            color = Neutral2,
            textAlign = TextAlign.Center
        )
        KocoButton(
            textContent = stringResource(id = R.string.payment_succeed_3),
            icon = null,
            onClick = onClicked
        )
    }
}

@Composable
fun PaymentDialogCanceled(
    onClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PADDING_SCREEN)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            )

    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.canceled),
            modifier = Modifier.size(IMAGE_SIZE),
            contentDescription = "Successful"
        )
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.payment_cancelled),
            color = Neutral1
        )
        KocoButton(
            textContent = stringResource(id = R.string.payment_cancelled_2),
            icon = null,
            onClick = onClicked
        )
    }
}

@Composable
fun PaymentDialogError(
    onClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PADDING_SCREEN)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            )
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.error),
            modifier = Modifier.size(IMAGE_SIZE),
            contentDescription = "Successful"
        )
        EcodemyText(
            format = Nunito.Heading2,
            data = "Payment Error",
            color = Neutral1
        )
        KocoButton(
            textContent = "Back to course", icon = null,
            onClick = onClicked
        )
    }
}