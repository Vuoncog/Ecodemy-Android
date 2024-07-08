package com.kltn.ecodemy.ui.wireframe.screens

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.constant.shimmerEffect
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.wireframe.components.WireframeCircle
import com.kltn.ecodemy.ui.wireframe.components.WireframeRect
import com.kltn.ecodemy.ui.wireframe.components.WireframeRectCircle

@Composable
fun HomeWireframe() {
   val alphaAnim = shimmerEffect()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(BackgroundColor)
            .padding(
                top = 16.dp,
            )
    ) {
        HomeWFUser(
            alpha = alphaAnim
        )
        Box(
            modifier = Modifier
                .height(188.dp)
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .alpha(alphaAnim)
                .background(Neutral4)
        )
        HomeWFCategory(
            alpha = alphaAnim
        )
        HomeWFCourse(
            alpha = alphaAnim
        )
    }
}

@Composable
fun HomeWFUser(
    alpha: Float,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        WireframeCircle(size = 40, alpha = alpha)
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            WireframeRect(
                height = 22, width = 128, border = 2,
                alpha = alpha
            )
            WireframeRect(
                height = 22, width = 48, border = 2,
                alpha = alpha
            )
        }
    }
}

@Composable
fun HomeWFCategory(
    alpha: Float,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(
                top = 12.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
    ) {
        WireframeRect(
            height = 28, width = 88, border = 2,
            alpha = alpha,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            (1..3).forEach {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(96.dp)
                ) {
                    WireframeCircle(
                        size = 72,
                        alpha = alpha
                    )
                    WireframeRect(
                        height = 22, width = 52, border = 2,
                        alpha = alpha
                    )
                }
            }
        }
    }
}

@Composable
fun HomeWFCourse(
    alpha: Float,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(
                top = 12.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
    ) {
        WireframeRect(height = 28, width = 88, border = 2, alpha = alpha)
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            (1..2).forEach {
                Column {
                    WireframeRect(height = 140, width = 248, border = 8, alpha = alpha)
                    Column(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        WireframeRect(height = 28, width = 248, border = 2, alpha = alpha)
                        WireframeRect(height = 14, width = 178, border = 2, alpha = alpha)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(
                                top = 2.dp,
                                bottom = 8.dp
                            )
                        ) {
                            (1..3).forEach {
                                WireframeRectCircle(height = 24, width = 62, alpha = alpha)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    WireframeRect(height = 22, width = 49, border = 2, alpha = alpha)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeWFPrev() {
    HomeWireframe()
}