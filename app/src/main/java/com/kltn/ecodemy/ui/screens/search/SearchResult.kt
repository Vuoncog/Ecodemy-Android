package com.kltn.ecodemy.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.components.SearchBar
import com.kltn.ecodemy.ui.screens.state.EmptyScreen
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.wireframe.screens.HomeWireframe

private val SEARCH_HEADER_BACKGROUND_HEIGHT = 72.dp
private val SEARCH_SCREEN_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)

@ExperimentalLayoutApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    paddingValues: PaddingValues,
    onTeacherCardClicked: (String) -> Unit,
    onCardClicked: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    searchViewModel: SearchViewModel,
) {
    val searchUiState = searchViewModel.searchUiState.collectAsState()
    val dataStatus = searchUiState.value
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    // Screen content
    when (dataStatus) {
        is RequestState.Success -> {
            SearchResult(
                onTeacherCardClicked = onTeacherCardClicked,
                searchViewModel = searchViewModel,
                onCardClicked = onCardClicked,
                onSearchTriggered = onSearchTriggered,
                onFilterClicked = {
                    showBottomSheet = true
                },
                paddingValues = paddingValues
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = Color.White
                ) {
                    SearchBottomSheet(
                        keywords = dataStatus.data.allKeywords,
                        checkedFilter = dataStatus.data.filterKeywords,
                        selectedKeyword = searchViewModel.selectedFilterKeywords,
                        resetFilterKeywords = {
                            searchViewModel.resetFilterKeywords()
                        }
                    )
                }
            }
        }

        is RequestState.Idle -> {
            EmptyScreen()
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

        }
    }
}

@ExperimentalLayoutApi
@Composable
fun SearchResult(
    onTeacherCardClicked: (String) -> Unit,
    searchViewModel: SearchViewModel,
    onCardClicked: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onFilterClicked: () -> Unit,
    paddingValues: PaddingValues
) {
    val searchUiState = searchViewModel.searchUiState.collectAsState()
    val dataStatus = searchUiState.value
    KocoScreen(
        headerBackgroundHeight = SEARCH_HEADER_BACKGROUND_HEIGHT,
        enableScrollable = false
    ) {
        when (dataStatus) {
            is RequestState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    item {
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
                    }

                    item {
                        SearchTeacher(
                            modifier = Modifier.fillMaxWidth(),
                            context = LocalContext.current,
                            teachers = dataStatus.data.teachers,
                            onTeacherItemClicked = onTeacherCardClicked
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    searchListCourses(
                        keyword = dataStatus.data.searchKeyword,
                        listCourses = dataStatus.data.courses,
                        onCardClicked = onCardClicked
                    )
                }
            }

            is RequestState.Idle -> {
                EmptyScreen()
            }

            is RequestState.Loading -> {
                HomeWireframe()
            }

            else -> {}
        }
    }
}

