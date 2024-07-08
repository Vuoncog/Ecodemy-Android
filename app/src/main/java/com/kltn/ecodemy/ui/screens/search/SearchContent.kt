package com.kltn.ecodemy.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.ui.components.CourseCardList
import com.kltn.ecodemy.ui.components.CourseCardListForLatestCourse
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.SearchBar
import com.kltn.ecodemy.ui.wireframe.screens.HomeWireframe

private val SEARCH_HEADER_BACKGROUND_HEIGHT = 72.dp
private val SEARCH_SCREEN_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)

@Composable
fun SearchContent(
    onSearchTriggered: (String) -> Unit,
    onCategoryItemClicked: (String) -> Unit,
    onCardClicked: (String) -> Unit,
    onFilterClicked: () -> Unit,
    searchViewModel: SearchViewModel,
    paddingValues: PaddingValues
) {
    when (val searchUiState = searchViewModel.searchUiState.collectAsState().value) {
        is RequestState.Success -> {
            KocoScreen(
                headerBackgroundHeight = SEARCH_HEADER_BACKGROUND_HEIGHT,
                enableScrollable = true
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    SearchBar(
                        value = searchViewModel.searchKeyword.value,
                        onValueChanged = { newValue ->
                            searchViewModel.searchKeyword.value = newValue
                        },
                        onSearchTriggered = onSearchTriggered,
                        onFilterClicked = onFilterClicked,
                        modifier = Modifier
                            .padding(Constant.PADDING_SCREEN)
                            .fillMaxWidth(),
                        searchKeywords = searchViewModel.selectedFilterKeywords.filter { it.value.first }
                            .map { it.value.second }.joinToString(",")
                    )
//                SearchCoursePack(
//                    coursePackTitle = "Course pack for Graphic designer",
//                    coursePackSlogan = "Master Swift UI, become a iOS developer",
//                    onDetailClicked = {}
//                )
                    CourseCardList(
                        title = stringResource(R.string.recommend_for_you),
                        onCardClicked = onCardClicked,
                        courses = searchUiState.data.coursesFromRecommender,
                        context = LocalContext.current
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    SearchCategory(
                        modifier = Modifier.fillMaxWidth(),
                        onCategoryItemClick = onCategoryItemClicked,
                        categories = searchUiState.data.category
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    if (searchUiState.data.recommendCourses.isNotEmpty()) {
                        val latestCourse = searchUiState.data.latestRecommendationCourse
                        CourseCardListForLatestCourse(
                            title = stringResource(R.string.you_have_seen),
                            latestCourse = latestCourse!!,
                            onCardClicked = onCardClicked,
                            courses = searchUiState.data.recommendCourses,
                            context = LocalContext.current
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    if (searchUiState.data.inCommonCourses.isNotEmpty()) {
                        CourseCardList(
                            title = stringResource(R.string.maybe_you_like),
                            onCardClicked = onCardClicked,
                            courses = searchUiState.data.inCommonCourses,
                            context = LocalContext.current
                        )
                    }
                }
            }

        }

        else -> {
            HomeWireframe()
        }
    }

}