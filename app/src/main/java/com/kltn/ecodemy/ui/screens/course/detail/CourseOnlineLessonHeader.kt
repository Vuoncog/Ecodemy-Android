package com.kltn.ecodemy.ui.screens.course.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary3

@Composable
fun CourseOnlineLessonHeader(
    totalLesson: String,
    totalTime: String,
) {
    val lessonHeaderMap = mapOf(
        R.drawable.book to "$totalLesson Lessons",
        R.drawable.time_five to totalTime,
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .horizontalScroll(rememberScrollState())
            .padding(PADDING_SCREEN)
            .padding(
                bottom = 8.dp
            )
    ) {
        lessonHeaderMap.forEach { (icon, label) ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = "book",
                    tint = Primary3
                )
                Text(
                    text = label,
                    style = Nunito.Subtitle1.textStyle,
                    color = Neutral1
                )
            }
        }
    }
}

@Composable
fun CourseOfflineLessonHeader(
    totalMembers: String,
    totalLesson: String,
    schedule: String,
) {
    val lessonHeaderMap = mapOf(
        R.drawable.user to totalMembers,
        R.drawable.time_five to totalLesson,
        R.drawable.calendar to schedule
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .horizontalScroll(rememberScrollState())
            .padding(PADDING_SCREEN)
            .padding(
                bottom = 8.dp
            )
    ) {
        lessonHeaderMap.forEach { (icon, label) ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = "book",
                    tint = Primary3
                )
                Text(
                    text = label,
                    style = Nunito.Subtitle1.textStyle,
                    color = Neutral1
                )
            }
        }
    }
}
