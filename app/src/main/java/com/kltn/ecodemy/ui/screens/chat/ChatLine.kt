package com.kltn.ecodemy.ui.screens.chat

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.epochSecondToLocalDateTime
import com.kltn.ecodemy.constant.formatToMessageDateString
import com.kltn.ecodemy.constant.formatToMessageTimeString
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2
import java.time.LocalDate

private val CHAT_USER_THUMBNAIL_SIZE = 32.dp
private val VERTICAL_MESSAGE_SPACE = Arrangement.spacedBy(4.dp)
private val CHAT_SPACING = 8.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatDate(
    localDate: LocalDate,
) {
    val formattedDate = localDate.formatToMessageDateString()
    EcodemyText(
        format = Nunito.Subtitle3,
        data = formattedDate,
        color = Neutral3,
        modifier = Modifier
            .padding(bottom = CHAT_SPACING)
            .fillMaxWidth()
            .padding(
                top = 2.dp,
                bottom = 2.dp
            ),
        textAlign = TextAlign.Center,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResponseLine(
    content: String,
    epochSecond: Long,
    imageRequest: ImageRequest,
    isGroup: Boolean = false,
    username: String = "",
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Constant.PADDING_SCREEN,
                end = Constant.PADDING_SCREEN
            )
            .padding(bottom = CHAT_SPACING)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Chat user",
            modifier = Modifier
                .size(CHAT_USER_THUMBNAIL_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(BackgroundColor)
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            verticalArrangement = VERTICAL_MESSAGE_SPACE
        ) {
            if (isGroup){
                EcodemyText(
                    format = Nunito.Subtitle2,
                    data = username,
                    color = Neutral3
                )
            }

            EcodemyText(
                format = Nunito.Body,
                data = content,
                color = Neutral1
            )
            EcodemyText(
                format = Nunito.Caption,
                data = epochSecond.epochSecondToLocalDateTime()
                    .formatToMessageTimeString(),
                color = Neutral2
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReplyLine(
    content: String,
    epochSecond: Long,
    imageRequest: ImageRequest,
    isGroup: Boolean = false,
    username: String = "",
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Constant.PADDING_SCREEN,
                end = Constant.PADDING_SCREEN
            )
            .padding(bottom = CHAT_SPACING)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Primary2)
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            verticalArrangement = VERTICAL_MESSAGE_SPACE
        ) {
            if (isGroup){
                EcodemyText(
                    format = Nunito.Subtitle2,
                    data = username,
                    color = Neutral3
                )
            }

            EcodemyText(
                format = Nunito.Body,
                data = content,
                color = Neutral1
            )
            EcodemyText(
                format = Nunito.Caption,
                data = epochSecond.epochSecondToLocalDateTime()
                    .formatToMessageTimeString(),
                color = Neutral2
            )
        }
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Chat user",
            modifier = Modifier
                .size(CHAT_USER_THUMBNAIL_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
