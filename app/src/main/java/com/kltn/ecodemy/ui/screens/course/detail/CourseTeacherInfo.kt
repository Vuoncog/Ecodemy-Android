package com.kltn.ecodemy.ui.screens.course.detail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.user.UserInfo
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Other1Darker

private val HORIZONTAL_PADDING = 16.dp
private val VERTICAL_PADDING = 8.dp

@Composable
fun CourseTeacherInfo(
    context: Context = LocalContext.current,
    teacher: UserInfo,
    paddingValues: PaddingValues = PaddingValues(
        vertical = VERTICAL_PADDING,
        horizontal = HORIZONTAL_PADDING
    )
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(teacher.avatar)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .background(Color.White)
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(Constant.HOME_AVATAR_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            EcodemyText(
                data = teacher.fullName,
                format = Nunito.Title1,
                color = Neutral1
            )
            EcodemyText(
                data = stringResource(id = R.string.route_teacher),
                format = Nunito.Subtitle1,
                color = Other1Darker
            )
        }
    }
}