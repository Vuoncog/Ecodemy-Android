package com.kltn.ecodemy.ui.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.CategoryViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.account.settings.SettingsTopBar
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onItemClicked: (String) -> Unit,
) {
    val categoryUiState = categoryViewModel.categoryUiState.collectAsState().value
    when (val courseList = categoryViewModel.courseList.collectAsState().value) {
        is RequestState.Success -> {
            Scaffold(
                containerColor = BackgroundColor,
                topBar = {
                    SettingsTopBar(
                        onBackClicked = onBackClicked,
                        title = stringResource(id = R.string.category_title)
                    )
                }
            ) {
                CategoryContent(
                    paddingValues = it,
                    courseList = courseList.data,
                    type = categoryUiState.type,
                    keyword = categoryUiState.keyword,
                    onItemClicked = onItemClicked
                )
            }
        }

        is RequestState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary1)
            }
        }

        else -> {
            Scaffold(
                containerColor = BackgroundColor,
                topBar = {
                    SettingsTopBar(
                        onBackClicked = onBackClicked,
                        title = stringResource(id = R.string.category_title)
                    )
                }
            ){
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
                        .padding(it)
                ) {
                    EcodemyText(
                        format = Nunito.Subtitle1,
                        data = "${categoryUiState.type}: ${categoryUiState.keyword}",
                        color = Neutral2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
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
                                imageVector = ImageVector.vectorResource(id = R.drawable.empty),
                                contentDescription = null,
                                modifier = Modifier.size(144.dp)
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            EcodemyText(
                                format = Nunito.Subtitle1,
                                data = stringResource(
                                    R.string.we_can_t_find_any_courses_with_category,
                                    categoryUiState.keyword
                                ),
                                color = Neutral2,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp)
                                    .padding(bottom = 56.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}