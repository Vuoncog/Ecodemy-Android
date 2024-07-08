package com.kltn.ecodemy.ui.screens.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.HOME_AVATAR_SIZE
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun HomeHeaderInfo(
    context: Context = LocalContext.current,
    onMessageClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    hide: Boolean = false,
    user: User,
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(user.userInfo.avatar)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Row(
        modifier = Modifier
            .padding(
                horizontal = PADDING_SCREEN,
                vertical = 8.dp
            )
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(HOME_AVATAR_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user.userInfo.fullName,
                style = Nunito.Title1.textStyle,
                color = Neutral1
            )
            Text(
                text = user.role.name,
                style = Nunito.Subtitle1.textStyle,
                color = user.role.color
            )
        }

        IconButton(onClick = onMessageClicked) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.message_rounded_dots),
                contentDescription = "Chat",
                tint = Neutral1
            )
        }
        if (!hide){
            IconButton(onClick = onNotificationClicked) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.bell),
                    contentDescription = "Bell",
                    tint = Neutral1
                )
            }
        }
    }
}