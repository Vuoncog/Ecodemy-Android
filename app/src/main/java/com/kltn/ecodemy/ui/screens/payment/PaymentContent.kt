package com.kltn.ecodemy.ui.screens.payment

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary3

private val PAYMENT_HEADER_BACKGROUND_HEIGHT = 56.dp

@Composable
fun PaymentContent(
    paddingValues: PaddingValues,
    onBackClicked: () -> Unit,
    orderMap: Map<String, Map<String, String>>,
    chosenMap: Map<String, Boolean>,
    onPaymentMethodClicked: (String) -> Unit,
) {
    KocoScreen(
        headerBackgroundHeight = PAYMENT_HEADER_BACKGROUND_HEIGHT,
        headerBackgroundShape = RoundedCornerShape(0)
    ) {
        //LazyColumn
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            //Sticky Header
            Row(
                modifier = Modifier
                    .height(PAYMENT_HEADER_BACKGROUND_HEIGHT)
                    .padding(horizontal = Constant.PADDING_SCREEN),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = "Back"
                    )
                }
                EcodemyText(
                    format = Nunito.Heading2,
                    data = stringResource(id = R.string.purchase_title),
                    color = Neutral1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
            }
            EcodemyText(
                format = Nunito.Title1,
                data = stringResource(id = R.string.payment_method),
                color = Neutral1,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .padding(bottom = 4.dp)
            )
            paymentMethodMap.forEach { (key, value) ->
                PaymentMethod(
                    icon = value,
                    method = key,
                    isChosen = chosenMap[key] ?: false,
                    infoMap = orderMap[key] ?: mapOf(),
                    onPaymentMethodClicked = onPaymentMethodClicked
                )
            }
        }
    }
}

@Composable
fun PaymentMethod(
    @DrawableRes icon: Int,
    method: String,
    isChosen: Boolean,
    infoMap: Map<String, String>,
    onPaymentMethodClicked: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onPaymentMethodClicked(method) }
                .background(Color.White)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = method
            )
            EcodemyText(format = Nunito.Title1, data = method, color = Neutral1, modifier = Modifier.weight(1f))
            if (isChosen) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.double_check),
                    contentDescription = "Double check",
                    tint = Primary1,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        if (isChosen) {
            if (infoMap.isNotEmpty()) {
                PaymentMethodInfo(infoMap = infoMap)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(156.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Primary3
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        EcodemyText(
                            format = Nunito.Subtitle1,
                            data = stringResource(id = R.string.payment_create_order),
                            color = Neutral2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethodInfo(
    infoMap: Map<String, String>,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        infoMap.forEach { (key, value) ->
            Row {
                EcodemyText(format = Nunito.Subtitle1, data = key, color = Neutral2)
                EcodemyText(
                    format = Nunito.Subtitle1, data = value, color = Neutral1,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

private val paymentMethodMap = mapOf(
    Constant.ZALO to R.drawable.zalo_pay,
//    Constant.MOMO to R.drawable.momo
)