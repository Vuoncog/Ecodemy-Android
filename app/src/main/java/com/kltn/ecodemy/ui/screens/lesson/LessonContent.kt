package com.kltn.ecodemy.ui.screens.lesson

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.models.course.Lecture
import com.kltn.ecodemy.domain.models.course.Lesson
import com.kltn.ecodemy.domain.models.course.Section
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.ui.components.CourseLessonSection
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonContent(
    paddingValues: PaddingValues,
    onBackClicked: () -> Unit,
    lecture: Lecture,
    sectionSelected: Section,
    lessonIndexInt: Int,
    courseTitle: String,
    teacherName: User,
    lessonSelected: Lesson,
    videoUri: String,
    resources: List<String>,
    onResourcesClick: (String) -> Unit,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
) {
    val onCheck = remember {
        mutableIntStateOf(1)
    }
    LazyColumn(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize(),
    ) {
        stickyHeader {
            LessonVideoPlayer(
                videoUri = videoUri,
                onBackClicked = onBackClicked
            )
        }
        item {
            LessonCourseInfo(
                courseTitle = courseTitle,
                teacherName = teacherName.userInfo.fullName,
            )
        }

        item {
            LessonSelectionTab(onCheck = onCheck)
        }

        lessonCourseTab(
            paddingValues = paddingValues,
            onCheck = onCheck,
            lecture = lecture,
            sectionSelected = sectionSelected,
            onResourcesClick = onResourcesClick,
            lessonIndexInt = lessonIndexInt,
            lessonSelected = lessonSelected,
            onLessonItemClicked = onLessonItemClicked,
            resources = resources
        )
    }
}

fun LazyListScope.lessonCourseTab(
    paddingValues: PaddingValues,
    onCheck: MutableState<Int>,
    lecture: Lecture,
    sectionSelected: Section,
    onResourcesClick: (String) -> Unit,
    lessonIndexInt: Int,
    lessonSelected: Lesson,
    resources: List<String>,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
) {
    when (onCheck.value) {
        1 -> {
            items(lecture.sections.size) {
                val section = lecture.sections[it]
                val isInSection = sectionSelected == section
                Log.d("isInSection", isInSection.toString())
                CourseLessonSection(
                    paddingValues = if (it == lecture.sections.size - 1) paddingValues else PaddingValues(),
                    sectionName = section.title,
                    lessons = section.lessons,
                    purchaseStatus = true,
                    sectionIndex = it,
                    isInSection = isInSection,
                    lessonIndexInt = lessonIndexInt,
                    lessonSelected = lessonSelected,
                    onLessonItemClicked = onLessonItemClicked
                )
            }
        }

        else -> items(resources) {
            Text(
                text = it,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(PADDING_SCREEN)
                    .clickable(onClick = { onResourcesClick(it) }),
                color = Neutral1,
                maxLines = 1
            )
        }
    }
}