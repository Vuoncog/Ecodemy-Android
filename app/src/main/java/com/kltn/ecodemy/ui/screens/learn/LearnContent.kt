package com.kltn.ecodemy.ui.screens.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.ErrorMessage.NON_LOGIN
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.constant.lessonPosition
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.viewmodels.LearnViewModel
import com.kltn.ecodemy.ui.screens.state.EmptyScreen
import com.kltn.ecodemy.ui.screens.state.NonLoginScreen
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.wireframe.screens.LearningWireframe

@Composable
fun LearnContent(
    paddingValues: PaddingValues,
    onCardClicked: (String) -> Unit,
    onLoginClicked: () -> Unit,
    // learnViewModel: LearnViewModel = hiltViewModel()
    learnViewModel: LearnViewModel
) {
    val learnUiState = learnViewModel.learnUiState.collectAsState()
    val dataStatus = learnUiState.value
    LaunchedEffect(key1 = true) {
        learnViewModel.refresh()
    }
    when (dataStatus) {
        is RequestState.Success -> {
            LearnSuccessfulContent(
                paddingValues = paddingValues,
                onSearchClicked = { /*TODO*/ },
                onCardClicked = onCardClicked,
                courses = dataStatus.data.courses,
            )
        }

        is RequestState.Idle -> {
            EmptyScreen()
        }

        is RequestState.Loading -> {
            LearningWireframe()
        }

        is RequestState.Error -> {
            if (dataStatus.error.message == NON_LOGIN){
                NonLoginScreen {
                    onLoginClicked()
                }
            }
        }
    }
}

@Composable
fun LearnSuccessfulContent(
    paddingValues: PaddingValues,
    onSearchClicked: () -> Unit,
    onCardClicked: (String) -> Unit,
    courses: List<Course>,
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(BackgroundColor)
            .fillMaxSize(),
    ) {
        item {
            LearnHeader(
                onSearchClicked = onSearchClicked
            )
        }

        items(courses) { course ->
            val splitter = course.progress.split("-")
            val progress = try {
                val lessonPosition = splitter[2].toInt().lessonPosition(splitter[1].toInt())
                "${splitter[1]}.${lessonPosition} ${splitter.last()}"
            } catch (e: Exception) {
                context.getString(R.string.start_your_first_lesson)
            }

            LearnCourseCard(
                context = context,
                course = course,
                progress = progress,
                onCardClicked = {
                    val arg = if (progress == context.getString(R.string.start_your_first_lesson)) {
                        "${course._id}-${1}-${1}"
                    } else {
                        val lessonPosition = splitter[2].toInt().lessonPosition(splitter[1].toInt())
                        "${course._id}-${splitter[1]}-${lessonPosition}"
                    }
                    onCardClicked(arg)
                },
            )
        }
    }
}