package com.kltn.ecodemy.ui.teacherscreens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.viewmodels.TeacherHomeViewModel
import com.kltn.ecodemy.ui.components.CenterBaseClassItem
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.home.HomeHeaderInfo
import com.kltn.ecodemy.ui.screens.state.EmptyScreen
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.ContainerColor
import com.kltn.ecodemy.ui.theme.EndLinear
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.StartLinear
import com.kltn.ecodemy.ui.wireframe.screens.HomeWireframe

private val CENTER_BASE_CLASS_PADDING = PaddingValues(
    start = 16.dp,
    end = 16.dp,
    top = 12.dp,
    bottom = 8.dp
)

@Composable
fun TeacherHomeScreen(
    paddingValues: PaddingValues,
    onMessageClicked: () -> Unit,
    onCardClicked: (String) -> Unit,
    teacherHomeViewModel: TeacherHomeViewModel = hiltViewModel()
) {
    val teacherHomeUiState = teacherHomeViewModel.teacherHomeUiState.collectAsState()
    val dataStatus = teacherHomeUiState.value.courses
    val user = teacherHomeUiState.value.user

    LaunchedEffect(Unit) {
        teacherHomeViewModel.refresh()
    }

    when (dataStatus) {
        is RequestState.Success -> {
            val onlCourses = dataStatus.data.filter { !it.type }
            val offCourses = dataStatus.data.filter { it.type }
            TeacherHomeSuccessfulContent(
                paddingValues = paddingValues,
                onCardClicked = onCardClicked,
                onlCourses = onlCourses,
                offCourses = offCourses,
                user = user,
                onMessageClicked = onMessageClicked
            )
        }

        is RequestState.Idle -> {
            EmptyScreen()
        }

        is RequestState.Loading -> {
            HomeWireframe()
        }

        else -> {

        }
    }
}

@Composable
fun TeacherHomeSuccessfulContent(
    paddingValues: PaddingValues,
    onMessageClicked: () -> Unit,
    onCardClicked: (String) -> Unit,
    onlCourses: List<Course>,
    offCourses: List<Course>,
    user: User? = User()
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(BackgroundColor)
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .clip(RoundedCornerShape(0))
//                .align(Alignment.TopStart)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(StartLinear, EndLinear),
                        start = Offset.Infinite,
                        end = Offset.Zero
                    )
                )
        ) {
            HomeHeaderInfo(
                onMessageClicked = onMessageClicked,
                user = user ?: User(),
                onNotificationClicked = {},
                hide = true
            )
        }
        if (onlCourses.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.38f)
                    .padding(bottom = 8.dp)
                    .background(ContainerColor)
                    .padding(
                        horizontal = 16.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    EcodemyText(
                        format = Nunito.Heading2,
                        data = stringResource(R.string.my_center_based_class),
                        color = Neutral1,
                        modifier = Modifier
                            .background(ContainerColor)
                            .fillMaxWidth()
                    )
                }
                items(onlCourses) { course ->
                    CenterBaseClassItem(
                        course = course,
                        onCardClicked = onCardClicked
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (offCourses.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .background(ContainerColor)
                    .padding(
                        horizontal = 16.dp,
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    EcodemyText(
                        format = Nunito.Heading2,
                        data = "My online class",
                        color = Neutral1,
                        modifier = Modifier
                            .background(ContainerColor)
                            .fillMaxWidth()
                    )
                }
                items(offCourses) { course ->
                    TeacherScreenCourseCard(
                        course = course,
                        enabled = true,
                        onCardClicked = onCardClicked
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TeacherHomeScreenPreview() {
//    TeacherHomeScreen(paddingValues = PaddingValues(0.dp))
//}