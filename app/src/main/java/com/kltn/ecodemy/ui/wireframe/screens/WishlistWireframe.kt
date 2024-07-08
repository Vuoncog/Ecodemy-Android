package com.kltn.ecodemy.ui.wireframe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.shimmerEffect
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.wireframe.components.WireframeRect
import com.kltn.ecodemy.ui.wireframe.components.WireframeRectCircle

@Composable
fun WishlistWireframe() {
    val alpha = shimmerEffect()
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        WishlistWFHeader()
        WishlistWFCourseItem(alpha = alpha)
    }
}

@Composable
fun WishlistWFHeader() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        EcodemyText(
            data = stringResource(id = R.string.route_wishlist),
            format = Nunito.Heading2,
            color = Neutral1,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WishlistWFCourseItem(
    alpha: Float
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(
            vertical = 8.dp,
            horizontal = 16.dp
        )
    ) {
        WireframeRect(height = 56, width = 56, border = 6, alpha = alpha)
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WireframeRectCircle(height = 14, width = 48, alpha = alpha)
                WireframeRectCircle(height = 14, width = 48, alpha = alpha)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .alpha(alpha)
                    .background(Neutral4)
            )
            WireframeRect(height = 14, width = 152, border = 2, alpha = alpha)
            WireframeRect(height = 20, width = 52, border = 2, alpha = alpha)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                WireframeRect(height = 14, width = 128, border = 2, alpha = alpha)
            }
        }
    }
}

