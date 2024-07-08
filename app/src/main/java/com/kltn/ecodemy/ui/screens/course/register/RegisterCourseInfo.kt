package com.kltn.ecodemy.ui.screens.course.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary3

@Composable
fun RegisterCourseInfo(
    course: Course
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(course.poster)
        .error(R.drawable.course)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()

    val mapSubInfo = mapOf(
        stringResource(R.string.start_date) to course.about.offCourse.classDate,
        stringResource(R.string.class_size) to "${course.about.offCourse.classSize} people",
        stringResource(R.string.lecture_info) to course.about.allCourse.lecture,
        stringResource(R.string.schedule) to course.about.offCourse.schedule
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
    ) {
        RegisterCourseMainInfo(
            image = imageRequest,
            courseTitle = course.title,
            courseTeacherName = course.teacher.userInfo.fullName
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Neutral4
        )
        RegisterCourseSubInfo(mapSubInfo = mapSubInfo)
    }
}

@Composable
fun RegisterCourseMainInfo(
    image: ImageRequest,
    courseTitle: String,
    courseTeacherName: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = "Course Image",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
        Column {
            EcodemyText(format = Nunito.Title1, data = courseTitle, color = Neutral1)
            EcodemyText(format = Nunito.Subtitle2, data = courseTeacherName, color = Neutral2)
        }
    }
}

@Composable
fun RegisterCourseSubInfo(
    mapSubInfo: Map<String, String>
) {
    mapSubInfo.forEach { (title, value) ->
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            EcodemyText(
                format = Nunito.Subtitle3, data = title, color = Neutral2,
                modifier = Modifier.weight(1f)
            )
            EcodemyText(format = Nunito.Subtitle1, data = value, color = Primary3)
        }
    }
}