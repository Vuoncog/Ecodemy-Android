package com.kltn.ecodemy.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.domain.models.course.Lesson
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun CourseLessonSection(
    paddingValues: PaddingValues = PaddingValues(),
    sectionName: String,
    sectionIndex: Int,
    purchaseStatus: Boolean,
    lessons: List<Lesson>,
    isInSection: Boolean = false,
    lessonIndexInt: Int = 0,
    lessonSelected: Lesson = Lesson(),
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
) {
    var onExpand by remember {
        mutableStateOf(true)
    }

    val lessonMap = lessons.lessonMap()
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(paddingValues)
            .padding(
                horizontal = PADDING_SCREEN,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EcodemyText(
                format = Nunito.Subtitle1,
                data = sectionName,
                color = Neutral2,
                modifier = Modifier.weight(1f)
            )
            Box(modifier = Modifier.clickable { onExpand = !onExpand }) {
                if (onExpand) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.minus),
                        contentDescription = "expend",
                        tint = Neutral1,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.plus),
                        contentDescription = "expend",
                        tint = Neutral1,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        if (onExpand) {
            lessonMap.entries.forEach { map ->
                val lessonIndexTemp = map.key.lessonIndex(sectionIndex+1)
                val les = map.value
                val pressed = lessonIndexTemp == lessonIndexInt &&
                        les == lessonSelected
                CourseLesson(
                    number = map.key.toString(),
                    time = "10:00",
                    pressed = pressed && isInSection,
                    purchaseStatus = purchaseStatus,
                    sectionIndex = sectionIndex,
                    lesson = les,
                    onLessonItemClicked = onLessonItemClicked
                )
            }
        }
    }
}

fun List<Lesson>.lessonMap(): Map<Int, Lesson> {
    return this.mapIndexed { index, lesson -> index + 1 to lesson }.toMap()
}

