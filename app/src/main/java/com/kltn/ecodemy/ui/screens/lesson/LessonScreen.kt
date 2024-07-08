package com.kltn.ecodemy.ui.screens.lesson

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.domain.viewmodels.LessonViewModel

@Composable
fun LessonScreen(
    lessonViewModel: LessonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onResourcesClick: (String) -> Unit,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
) {
    val lessonUiState = lessonViewModel.learnUiState.collectAsState()
    val videoUri = lessonUiState.value.videoUrl

    BackHandler {
        onBackClicked()
    }

    Scaffold(
        content = {
            LessonContent(
                paddingValues = it,
                onBackClicked = onBackClicked,
                lecture = lessonUiState.value.lecture,
                sectionSelected = lessonUiState.value.sectionSelected,
                lessonIndexInt = lessonUiState.value.lessonIndex,
                lessonSelected = lessonUiState.value.lessonSelected,
                videoUri = videoUri,
                onResourcesClick = onResourcesClick,
                resources = lessonUiState.value.resources,
                courseTitle = lessonUiState.value.courseTitle,
                teacherName = lessonUiState.value.courseTeacher,
                onLessonItemClicked = onLessonItemClicked
            )
        }
    )
}