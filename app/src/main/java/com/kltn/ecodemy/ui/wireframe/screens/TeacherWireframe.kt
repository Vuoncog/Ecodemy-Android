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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.shimmerEffect
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.wireframe.components.WireframeCircle
import com.kltn.ecodemy.ui.wireframe.components.WireframeRect

@Composable
fun TeacherWireframe() {
    val alpha = shimmerEffect()
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        TeacherWFHeader()
        TeacherWFAvatar(alpha = alpha)
        Spacer(modifier = Modifier.height(20.dp))
        TeacherWFIntroduction(alpha = alpha)
        Spacer(modifier = Modifier.height(20.dp))
        TeacherWFCourse(alpha = alpha)
    }
}

@Composable
fun TeacherWFHeader() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
            contentDescription = "Search",
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
            tint = Neutral1
        )
        EcodemyText(
            data = stringResource(id = R.string.route_teacher),
            format = Nunito.Heading2,
            color = Neutral1,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(40.dp))
    }
}

@Composable
fun TeacherWFAvatar(
    alpha: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                bottom = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WireframeCircle(size = 96, alpha = alpha)
        Spacer(modifier = Modifier.height(12.dp))
        WireframeRect(height = 28, width = 210, border = 2, alpha = alpha)
        Spacer(modifier = Modifier.height(8.dp))
        WireframeRect(height = 22, width = 136, border = 2, alpha = alpha)
    }
}

@Composable
fun TeacherWFIntroduction(
    alpha: Float
) {
    Column(
        modifier = Modifier.padding(
            top = 12.dp,
            bottom = 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
    ) {
        WireframeRect(height = 28, width = 118, border = 2, alpha = alpha)
        Spacer(modifier = Modifier.height(12.dp))
        (1..3).forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .alpha(alpha)
                    .background(Neutral4)
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        WireframeRect(height = 12, width = 72, border = 2, alpha = alpha)
    }
}

@Composable
fun TeacherWFCourse(
    alpha: Float,
) {
    Column(
        modifier = Modifier.padding(
            top = 12.dp,
            bottom = 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
    ) {
        WireframeRect(height = 28, width = 118, border = 2, alpha = alpha)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WireframeCircle(size = 72, alpha = alpha)
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .alpha(alpha)
                        .background(Neutral4)
                )
                WireframeRect(height = 14, width = 156, border = 2, alpha = alpha)
                WireframeRect(height = 12, width = 88, border = 2, alpha = alpha)
                WireframeRect(height = 20, width = 56, border = 2, alpha = alpha)
            }
        }
    }
}

@Preview
@Composable
fun TeacherWFPrev() {
    TeacherWireframe()
}