package com.kltn.ecodemy.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.user.UserInfo
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

private val TEACHER_ITEM_WIDTH = 96.dp
private val TEACHER_ITEM_IMAGE_SIZE = 60.dp

@Composable
fun TeacherItem(
    context: Context,
    teacherId: String,
    teacher: UserInfo,
    onClick: (String) -> Unit,
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(teacher.avatar)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .width(TEACHER_ITEM_WIDTH)
            .clickable(onClick = {
                onClick(teacherId)
            })
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(TEACHER_ITEM_IMAGE_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        EcodemyText(
            data = teacher.fullName,
            format = Nunito.Subtitle3,
            color = Neutral2,
            textAlign = TextAlign.Center
        )
    }
}