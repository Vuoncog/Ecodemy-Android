package com.kltn.ecodemy.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.ui.screens.state.EmptyScreen
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.ContainerColor
import com.kltn.ecodemy.ui.wireframe.screens.HomeWireframe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    onSearchTriggered: (String) -> Unit,
    searchViewModel: SearchViewModel,
    onCategoryItemClicked: (String) -> Unit,
    onCardClicked: (String) -> Unit,
) {
    val searchUiState = searchViewModel.searchUiState.collectAsState()
    val dataStatus = searchUiState.value
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        searchViewModel.refreshData()
    }
    // Screen content
    when (dataStatus) {
        is RequestState.Success -> {
            SearchContent(
                paddingValues = paddingValues,
                onSearchTriggered = onSearchTriggered,
                onFilterClicked = {
                    showBottomSheet = true
                },
                onCategoryItemClicked = onCategoryItemClicked,
                searchViewModel = searchViewModel,
                onCardClicked = onCardClicked,
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = ContainerColor,
                ) {
                    // Sheet content
                    SearchBottomSheet(
                        keywords = dataStatus.data.allKeywords,
                        checkedFilter = "",
                        selectedKeyword = searchViewModel.selectedFilterKeywords,
                        resetFilterKeywords = {
                            searchViewModel.resetFilterKeywords()
                        }
                    )
                    //                    onFilterClicked = {
                    //                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                    //                            if (!sheetState.isVisible) {
                    //                                showBottomSheet = false
                    //                            }
                    //                        }
                    //                    }
                }
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