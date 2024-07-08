package com.kltn.ecodemy.ui.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.viewmodels.HomeViewModel
import com.kltn.ecodemy.ui.components.CourseCardList
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.PopularCourseCardList
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.wireframe.screens.HomeWireframe

private val HOME_SCREEN_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)

@ExperimentalFoundationApi
@Composable
fun HomeContent(
    onMessageClicked: () -> Unit,
    onRefreshClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onCardClicked: (String) -> Unit,
    onTeacherItemClicked: (String) -> Unit,
    paddingValues: PaddingValues,
    showBottomBar: (Boolean) -> Unit,
    onCategoryItemClicked: (String) -> Unit,
    homeViewModel: HomeViewModel,
) {
    val homeUiState = homeViewModel.homeUiState.collectAsState()
    val dataStatus = homeUiState.value.courses
    val teachers = homeUiState.value.teachers
    val user = homeUiState.value.user

    LaunchedEffect(key1 = Unit) {
        homeViewModel.setUser()
    }

    when (dataStatus) {
        is RequestState.Success -> {
            showBottomBar(true)
            KocoScreen(
                headerBackgroundHeight = 300.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues),
                    verticalArrangement = HOME_SCREEN_SPACE_BETWEEN
                ) {
                    HomeHeaderInfo(
                        onMessageClicked = onMessageClicked,
                        user = user ?: User(),
                        onNotificationClicked = onNotificationClicked
                    )
                    Log.d("AdsList", homeUiState.value.adsList.toString())
                    HomeAds(
                        listAds = homeUiState.value.adsList,
                        onCardClicked = onCardClicked
                    )
                    HomeCategory(
                        modifier = Modifier.fillMaxWidth(),
                        onCategoryItemClick = onCategoryItemClicked
                    )
                    PopularCourseCardList(
                        title = stringResource(id = R.string.popular_title),
                        onCardClicked = onCardClicked,
                        courses = dataStatus.data,
                        context = LocalContext.current
                    )
                    CourseCardList(
                        title = stringResource(R.string.latest_courses),
                        onCardClicked = onCardClicked,
                        courses = homeUiState.value.latestCourses,
                        context = LocalContext.current
                    )
//            HomeRecommendCourse(
//                onCardClicked = onCardClicked
//            )
                    HomeTeacher(
                        modifier = Modifier.fillMaxWidth(),
                        context = LocalContext.current,
                        teachers = teachers,
                        onTeacherItemClicked = onTeacherItemClicked
                    )
                }
            }
        }

        is RequestState.Idle -> {
            showBottomBar(true)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EcodemyText(
                    data = stringResource(R.string.server_error),
                    format = Nunito.Heading2,
                    color = Neutral1
                )
            }
        }

        is RequestState.Loading -> {
            showBottomBar(false)
            HomeWireframe()
        }

        else -> {
            showBottomBar(false)
            HomeError(onRefreshClicked = onRefreshClicked)
        }
    }
}

@Composable
fun HomeError(
    onRefreshClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = Constant.PADDING_SCREEN,
            )
            .padding(
                top = 12.dp,
                bottom = 8.dp
            )
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_wifi),
                    contentDescription = null,
                    modifier = Modifier.size(144.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                EcodemyText(
                    format = Nunito.Subtitle1,
                    data = stringResource(R.string.unstable_connection),
                    color = Neutral2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 56.dp)
                )

                KocoButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp
                        ),
                    textContent = "Refresh", icon = null
                ) {
                    onRefreshClicked()
                }
            }
        }
    }
}