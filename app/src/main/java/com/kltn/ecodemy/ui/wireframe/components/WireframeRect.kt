package com.kltn.ecodemy.ui.wireframe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.theme.Neutral4

@Composable
fun WireframeRect(
    height: Int,
    width: Int,
    border: Int,
    alpha: Float = 1f,
) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .clip(RoundedCornerShape(border.dp))
            .alpha(alpha)
            .background(Neutral4)

    )
}