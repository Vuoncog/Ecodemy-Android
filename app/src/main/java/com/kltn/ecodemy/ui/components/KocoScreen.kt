package com.kltn.ecodemy.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.EndLinear
import com.kltn.ecodemy.ui.theme.StartLinear

private val BORDER_SHAPE = RoundedCornerShape(0)
private val HEADER_BACKGROUND_HEIGHT = 272.dp

@Composable
fun KocoScreen(
    modifier: Modifier = Modifier,
    headerBackgroundHeight: Dp = HEADER_BACKGROUND_HEIGHT,
    headerBackgroundShape: Shape = BORDER_SHAPE,
    enableScrollable: Boolean = true,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .scrollable(
                enableScrollable = enableScrollable,
                state = rememberScrollState()
            )
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerBackgroundHeight)
                .clip(headerBackgroundShape)
                .align(Alignment.TopStart)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(StartLinear, EndLinear),
                        start = Offset.Infinite,
                        end = Offset.Zero
                    )
                )
        )
        content()
    }
}

@Composable
fun Modifier.scrollable(enableScrollable: Boolean, state: ScrollState) =
    if (enableScrollable) {
        this then Modifier.verticalScroll(state)
    } else {
        this then Modifier
    }

@Preview
@Composable
fun BackgroundScreenPrev() {
    KocoScreen {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Hello world")
        }
    }
}