package com.kltn.ecodemy.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.theme.ContainerColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary3

@Composable
fun CenterBaseClassItem(
    context: Context = LocalContext.current,
    course: Course,
    onCardClicked: (String) -> Unit
){
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .error(R.drawable.course)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()
    Row (
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .background(ContainerColor)
            .clickable { onCardClicked(course._id) }
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier.padding(top = 4.dp),
        ) {
            EcodemyText(
                data = course.title,
                format = Nunito.Title1,
                color = Neutral1,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(2.dp))
            EcodemyText(
                data = course.teacher.userInfo.fullName,
                format = Nunito.Subtitle3,
                color = Neutral2,
            )
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.time_five),
                    contentDescription = "Clock",
                    tint = Primary3,
                    modifier = Modifier.size(24.dp)
                )
                EcodemyText(format = Nunito.Title1, data = "17:30", color = Neutral1)
            }
        }
    }
}

//@Preview (showBackground = true)
//@Composable
//fun CenterBaseClassPreview() {
//    CenterBaseClass()
//}