package com.kltn.ecodemy.ui.screens.notification

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun NotificationItem(
    image: String,
    title: String,
    content: String,
    time: String,
) {
    val context: Context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(image)
        .placeholder(R.drawable.ecodemy_logo)
        .error(R.drawable.ecodemy_logo)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = Constant.PADDING_SCREEN
            )
            .padding(
                bottom = 16.dp,
                top = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageRequest),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                EcodemyText(
                    format = Nunito.Subtitle1, data = title, color = Neutral1,
                    maxLines = 1
                )
                EcodemyText(
                    format = Nunito.Body,
                    data = content,
                    color = Neutral2
                )
                EcodemyText(
                    format = Nunito.Subtitle2,
                    data = time,
                    color = Neutral3
                )
            }
        }
    }
}