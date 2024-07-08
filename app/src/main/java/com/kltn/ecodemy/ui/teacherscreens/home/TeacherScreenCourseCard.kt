package com.kltn.ecodemy.ui.teacherscreens.home

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.ContainerColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary3

@Composable
fun TeacherScreenCourseCard(
    context: Context = LocalContext.current,
    course: Course,
    enabled: Boolean = false,
    onCardClicked: (String) -> Unit,
) {
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .error(R.drawable.course)
        .placeholder(R.drawable.course)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
                .clickable(enabled) { onCardClicked(course._id) }
                .background(ContainerColor)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(6.dp)),
                model = imageRequest,
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                EcodemyText(
                    format = Nunito.Title1,
                    data = course.title,
                    color = Neutral1
                )
                EcodemyText(
                    format = Nunito.Subtitle2,
                    data = course.teacher.userInfo.fullName,
                    color = Neutral2
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun EditResources(
    onEditResourcesClicked: () -> Unit,
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Row(
        modifier = Modifier
            .drawBehind {
                drawRoundRect(
                    color = Primary3,
                    style = stroke,
                    cornerRadius = CornerRadius(2.dp.toPx())
                )
            }
            .fillMaxWidth()
            .padding(
                horizontal = 6.dp
            )
            .clickableWithoutRippleEffect(true) {
                onEditResourcesClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.x), contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Primary1
        )

        Spacer(modifier = Modifier.width(10.dp))

        EcodemyText(
            format = Nunito.Title1,
            data = stringResource(R.string.edit_resources),
            color = Neutral3
        )
    }
}