package com.kltn.ecodemy.ui.wireframe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.theme.Neutral4

@Composable
fun WireframeCircle(
    size: Int,
    alpha: Float = 1f,
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .alpha(alpha)
            .background(Neutral4)
    )
}