package com.kltn.ecodemy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.domain.models.course.Lesson
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2
import com.kltn.ecodemy.ui.theme.Primary3

@Composable
fun CourseLesson(
    number: String,
    time: String,
    pressed: Boolean,
    purchaseStatus: Boolean,
    sectionIndex: Int,
    lesson: Lesson,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
) {
    val backgroundColor = remember { mutableStateOf(Color.Transparent) }
    val numberContainerColor = remember { mutableStateOf(Primary2) }
    val numberColor = remember { mutableStateOf(Primary3) }
    val titleColor = remember { mutableStateOf(Neutral1) }

    LaunchedEffect(key1 = pressed) {
        if (pressed) {
            backgroundColor.value = Primary2
            numberContainerColor.value = Primary3
            numberColor.value = Primary2
            titleColor.value = Primary3
        } else {
            backgroundColor.value = Color.Transparent
            numberContainerColor.value = Primary2
            numberColor.value = Primary3
            titleColor.value = Neutral1
        }
    }
    Row(
        modifier = Modifier
            .background(color = backgroundColor.value, shape = RoundedCornerShape(12.dp))
            .clickable(purchaseStatus) {
                onLessonItemClicked(
                    sectionIndex + 1,
                    number
                        .toInt()
                        .lessonIndex(sectionIndex + 1)
                )
            }
            .padding(
                top = 8.dp,
                start = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = numberContainerColor.value, shape = RoundedCornerShape(20.dp))
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = number,
                color = numberColor.value
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = lesson.title,
                color = titleColor.value
            )
//            Text(
//                text = time,
//                style = Nunito.Subtitle1.textStyle,
//                color = Neutral2
//            )
        }
    }
}
