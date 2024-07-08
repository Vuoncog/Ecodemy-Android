package com.kltn.ecodemy.ui.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.teacher.TeacherCourseInfo
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun CategoryContent(
    paddingValues: PaddingValues,
    courseList: List<Course>,
    type: String,
    keyword: String,
    onItemClicked: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = Constant.PADDING_SCREEN,
            )
            .padding(
                top = 12.dp,
                bottom = 8.dp
            )
            .padding(paddingValues)
    ) {
        item {
            EcodemyText(
                format = Nunito.Subtitle1, data = "$type: $keyword", color = Neutral2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        items(courseList) {
            TeacherCourseInfo(
                course = it,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp),
                onItemClicked = onItemClicked
            )
        }
    }
}