package com.kltn.ecodemy.ui.screens.teacher

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.ProfileTab

private val TEACHER_HEADER_BACKGROUND_HEIGHT = 56.dp

@Composable
fun TeacherContent(
    paddingValues: PaddingValues,
    image: String,
    teacherName: String,
    teacherJob: String,
    about: String,
    listCourses: List<Course> = emptyList(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileTab(
            image = image,
            profileName = teacherName,
            job = teacherJob
        )

//        Spacer(modifier = Modifier.height(8.dp))
//        TeacherIntroduction(about = about)

        Spacer(modifier = Modifier.height(8.dp))
        TeacherListCourses(
            listCourses = listCourses,
            teacherName = teacherName
        )
    }
}