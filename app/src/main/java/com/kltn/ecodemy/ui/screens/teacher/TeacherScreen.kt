package com.kltn.ecodemy.ui.screens.teacher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.TeacherUiState
import com.kltn.ecodemy.domain.viewmodels.TeacherViewModel
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.wireframe.screens.TeacherWireframe

@Composable
fun TeacherScreen(
    teacherViewModel: TeacherViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val teacherUiState = teacherViewModel.teacherUiState.collectAsState()
    when (teacherUiState.value) {
        is RequestState.Success -> {
            val teacherData = (teacherUiState.value as RequestState.Success<TeacherUiState>).data.user
            val teacherCourses = (teacherUiState.value as RequestState.Success<TeacherUiState>).data.listCourses
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = BackgroundColor,
                topBar = {
                    TeacherTopBar(
                        onBackClicked = onBackClicked
                    )
                },
                content = { paddingValues ->
                    TeacherContent(
                        paddingValues = paddingValues,
                        image = teacherData.userInfo.avatar,
                        teacherName = teacherData.userInfo.fullName,
                        teacherJob = teacherData.userInfo.email,
                        listCourses = teacherCourses,
                        about = teacherData.userInfo.phone
                    )
                }
            )
        }
        else -> {
            TeacherWireframe()
        }
    }
}