package com.kltn.ecodemy.ui.screens.account.settings.history

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserInfo
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun SettingsHistoryItem(
    paymentDate: String,
    course: Course,
    teacherData: User,
) {
    val context: Context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .placeholder(R.drawable.ecodemy_logo)
        .error(R.drawable.ecodemy_logo)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = PADDING_SCREEN
            )
            .padding(
                bottom = 16.dp,
                top = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        EcodemyText(
            data = paymentDate,
            format = Nunito.Subtitle2,
            color = Neutral2
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageRequest),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f)
            ) {
                EcodemyText(format = Nunito.Subtitle1, data = course.title, color = Neutral1)
                EcodemyText(
                    format = Nunito.Subtitle2,
                    data = teacherData.userInfo.fullName,
                    color = Neutral1
                )
            }
            EcodemyText(
                format = Nunito.Subtitle3,
                data = "$ ${course.price}",
                color = Neutral1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsHistoryItemPrev() {
    SettingsHistoryItem(
        teacherData = User(
            userInfo = UserInfo(
                fullName = "Maximilian Schwarzmüller"
            )
        ),
        paymentDate = "31 Nem", course = Course(
            title = "Flutter & Dart - The Complete Guide",
            price = 19.99,
            teacher = User(
                userInfo = UserInfo(
                    fullName = "Maximilian Schwarzmüller"
                )
            ),
        )
    )
}