package com.kltn.ecodemy.ui.screens.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun LessonCourseInfo(
    courseTitle: String,
    teacherName: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .background(Color.White)
            .padding(
                horizontal = Constant.PADDING_SCREEN,
            )
            .padding(
                top = 8.dp,
                bottom = 16.dp
            )
    ) {
        EcodemyText(
            format = Nunito.Title1,
            data = courseTitle,
            color = Neutral1,
            modifier = Modifier
                .fillMaxWidth()
        )
        EcodemyText(
            format = Nunito.Subtitle1,
            data = teacherName,
            color = Neutral2,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}