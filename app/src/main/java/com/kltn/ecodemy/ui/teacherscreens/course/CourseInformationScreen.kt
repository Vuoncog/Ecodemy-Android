package com.kltn.ecodemy.ui.teacherscreens.course

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.course.Lecture
import com.kltn.ecodemy.domain.models.course.Lesson
import com.kltn.ecodemy.domain.viewmodels.TeacherCourseViewModel
import com.kltn.ecodemy.ui.components.CourseLesson
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.teacherscreens.home.EditResources
import com.kltn.ecodemy.ui.teacherscreens.home.TeacherScreenCourseCard
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.delay

@Composable
fun CourseInformationScreen(
    teacherCourseViewModel: TeacherCourseViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val isUpdated = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val teacherCourseUiState = teacherCourseViewModel.teacherCourseUiState.collectAsState()
    when (val course = teacherCourseUiState.value.course) {
        is RequestState.Success -> {
            Scaffold(
                topBar = {
                    TeacherCourseHeader(
                        onBackClicked = onBackClicked,
                        showTrailingIcon = isUpdated.value,
                        onTrailingIconClicked = {
                            isUpdated.value = false
                        }
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        )
                        .padding(paddingValues),
                ) {
                    item {
                        TeacherScreenCourseCard(
                            course = course.data,
                            onCardClicked = {}
                        )
                        if (isUpdated.value) {
                            KocoButton(
                                textContent = "Update resources", icon = null,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                teacherCourseViewModel.loading(true)
                                teacherCourseViewModel.uploadResources {
                                    Toast.makeText(context, "Course is updated", Toast.LENGTH_SHORT).show()
                                    isUpdated.value = false
                                    teacherCourseViewModel.loading(false)
                                }
                            }
                        } else {
                            EditResources {
                                isUpdated.value = true
                            }
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                    item {
                        EcodemyText(format = Nunito.Heading2, data = "Resources", color = Neutral1)
                        Spacer(modifier = Modifier.size(8.dp))
                    }

                    lecture(
                        lecture = course.data.lecture,
                        isUpdated = isUpdated.value,
                        onAddResources = teacherCourseViewModel::addResources
                    )

                }
            }
            if (teacherCourseUiState.value.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary1)
                }
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary1)
            }
        }
    }
}

private fun LazyListScope.lecture(
    lecture: Lecture,
    isUpdated: Boolean = false,
    onAddResources: (sectionIndex: Int, lessonIndex: Int, file: Uri) -> Unit,
) {
    itemsIndexed(lecture.sections) { index, section ->
        TeacherScreenCourseLessonSection(
            sectionName = section.title,
            sectionIndex = index,
            lessons = section.lessons,
            lessonData = mutableListOf(),
            isUpdated = isUpdated,
            onAddResources = onAddResources
        )
    }
}

@Composable
fun TeacherScreenCourseLessonSection(
    paddingValues: PaddingValues = PaddingValues(),
    isUpdated: Boolean = false,
    sectionName: String,
    sectionIndex: Int,
    lessons: List<Lesson>,
    lessonData: MutableList<String>,
    lessonIndex: List<String> = emptyList(),
    onAddResources: (sectionIndex: Int, lessonIndex: Int, file: Uri) -> Unit,
) {
    var onExpand by remember {
        mutableStateOf(true)
    }

    val lessonMap = lessons.lessonMap()
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(paddingValues)
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
                val lessonArg = mutableListOf<String>()
                lessonArg.addAll(lessonData)
                lessonArg.add(sectionName)
                val index = listOf(
                    sectionName,
                    sectionIndex.lessonIndex(map.key).toString(),
                    map.value.title
                )
                ResourcesCourseLesson(
                    number = map.key.toString(),
                    time = "10:00",
                    pressed = index == lessonIndex,
                    sectionIndex = sectionIndex,
                    lesson = map.value,
                    isUpdated = isUpdated,
                    onAddResources = onAddResources
                )
            }
        }
    }
}

fun List<Lesson>.lessonMap(): Map<Int, Lesson> {
    return this.mapIndexed { index, lesson -> index + 1 to lesson }.toMap()
}