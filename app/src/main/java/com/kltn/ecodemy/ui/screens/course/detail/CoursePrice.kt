package com.kltn.ecodemy.ui.screens.course.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.components.EcodemyAnnotatedText
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.KocoOutlinedButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1


@Composable
fun CourseOnlinePrice(
    price: Double,
    salePrice: Double,
    isLogout: Boolean,
    onLoginClicked: () -> Unit,
    wishlistStatus: Boolean,
    purchaseStatus: Boolean,
    onPurchaseClicked: () -> Unit,
    onWishlistClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = stringResource(id = R.string.course_price),
                style = Nunito.Subtitle1.textStyle,
                color = Neutral3
            )
            if (salePrice == 0.0) {
                EcodemyText(
                    data = "$ $price",
                    format = Nunito.Heading2,
                    color = Neutral1
                )
            } else {
                EcodemyText(
                    data = "$ $price",
                    format = Nunito.Title1,
                    color = Neutral2,
                    textDecoration = TextDecoration.LineThrough,
                )
                EcodemyText(
                    data = "$ $salePrice",
                    format = Nunito.Heading2,
                    color = Neutral1
                )
            }
        }
        if (purchaseStatus) {
            KocoButton(
                textContent = stringResource(R.string.you_already_purchased),
                icon = null,
                disable = true,
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            if (isLogout) {
                KocoButton(
                    textContent = stringResource(id = R.string.login_title),
                    icon = null,
                    onClick = onLoginClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                KocoButton(
                    textContent = stringResource(id = R.string.purchase_title),
                    icon = null,
                    onClick = onPurchaseClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
        if (wishlistStatus) {
            KocoOutlinedButton(
                textContent = stringResource(id = R.string.course_remove),
                icon = null,
                onClick = onWishlistClicked,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            if (!isLogout) {
                KocoOutlinedButton(
                    textContent = stringResource(id = R.string.course_add),
                    icon = null,
                    onClick = onWishlistClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CourseOfflinePrice(
    price: Double,
    salePrice: Double,
    wishlistStatus: Boolean,
    purchaseStatus: Boolean,
    isLogout: Boolean,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onWishlistClicked: () -> Unit,
    onContactClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = stringResource(R.string.price),
                style = Nunito.Subtitle1.textStyle,
                color = Neutral3
            )
            if (salePrice == 0.0) {
                EcodemyText(
                    data = "$ $price",
                    format = Nunito.Heading2,
                    color = Neutral1
                )
            } else {
                EcodemyText(
                    data = "$ $price",
                    format = Nunito.Title1,
                    color = Neutral2,
                    textDecoration = TextDecoration.LineThrough,
                )
                EcodemyText(
                    data = "$ $salePrice",
                    format = Nunito.Heading2,
                    color = Neutral1
                )
            }
        }
        if (!isLogout) {
            KocoButton(
                textContent = if (purchaseStatus) stringResource(R.string.you_already_registered)
                else stringResource(R.string.register_course),
                icon = null,
                disable = purchaseStatus,
                onClick = onRegisterClicked,
                modifier = Modifier.fillMaxWidth()
            )
            KocoOutlinedButton(
                textContent = "Chat to consultant",
                icon = null,
                onClick = onContactClicked,
                modifier = Modifier.fillMaxWidth()
            )
            if (wishlistStatus) {
                KocoOutlinedButton(
                    textContent = "Added to wishlist!",
//                disable = true,
                    icon = null,
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                KocoOutlinedButton(
                    textContent = "Add to wishlist",
                    icon = null,
                    onClick = onWishlistClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            KocoButton(
                textContent = stringResource(R.string.register_course),
                icon = null,
                disable = purchaseStatus,
                onClick = onRegisterClicked,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LoginRequiredText() {
    EcodemyText(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .padding(
                vertical = 8.dp,
                horizontal = 48.dp
            ),
        textAlign = TextAlign.Center,
        format = Nunito.Title2,
        color = Neutral2,
        data = stringResource(R.string.login_required)
    )
}

@Composable
fun UpgradeText() {
    EcodemyAnnotatedText(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .padding(
                vertical = 8.dp,
                horizontal = 48.dp
            ),
        textAlign = TextAlign.Center,
        format = Nunito.Title2,
        data = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Neutral3,
                )
            ) {
                append(stringResource(R.string.you_will_be_upgraded_to))
            }

            withStyle(
                style = SpanStyle(
                    color = Primary1,
                )
            ) {
                append(stringResource(R.string.student))
            }

            withStyle(
                style = SpanStyle(
                    color = Neutral3,
                )
            ) {
                append(stringResource(R.string.after_owning_first_course))
            }
        }
    )
}