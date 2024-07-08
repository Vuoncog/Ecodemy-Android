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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.shimmerEffect
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.wireframe.components.WireframeRect

@Composable
fun LearningWireframe() {
    val alpha = shimmerEffect()
    Column(
        modifier= Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        LearningWFHeader()
        LearningWFCourseItem(alpha = alpha)
    }
}

@Composable
fun LearningWFHeader() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(40.dp))
        EcodemyText(
            data = stringResource(id = R.string.learn_title),
            format = Nunito.Heading2,
            color = Neutral1,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(Constant.ICON_INTERACTIVE_SIZE))
//        Icon(
//            imageVector = ImageVector.vectorResource(R.drawable.search),
//            contentDescription = "Search",
//            modifier = Modifier
//                .padding(8.dp)
//                .size(24.dp)
//            ,
//            tint = Neutral1
//        )
    }
}

@Composable
fun LearningWFCourseItem(
    alpha: Float,
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
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = 0.5f,
                trackColor = Neutral4,
                color = Neutral3,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .clip(CircleShape)
                    .alpha(alpha)
            )
            WireframeRect(height = 14, width = 92, border = 2, alpha = alpha)
        }
    }
}