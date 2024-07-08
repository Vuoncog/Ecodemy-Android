package com.kltn.ecodemy.ui.screens.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.StartLinear

private val WL_HEADER_BACKGROUND_HEIGHT = 56.dp

@Composable
fun WishlistHeader() {
    Row(
        modifier = Modifier
            .background(StartLinear)
            .height(WL_HEADER_BACKGROUND_HEIGHT)
            .padding(horizontal = Constant.PADDING_SCREEN),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.route_wishlist),
            color = Neutral1,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}