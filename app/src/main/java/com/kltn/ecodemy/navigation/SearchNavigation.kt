package com.kltn.ecodemy.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.ui.screens.search.SearchResultScreen
import com.kltn.ecodemy.ui.screens.search.SearchScreen

@ExperimentalLayoutApi
fun NavGraphBuilder.searchNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    onCardClicked: (String) -> Unit,
    navigateToHome: () -> Unit,
    onTeacherCardClicked: (String) -> Unit,
    onNextSearch: (String) -> Unit,
    searchViewModel: SearchViewModel,
    showBottomBar: (Boolean) -> Unit,
    onCategoryItemClicked: (String, String) -> Unit,
) {
    navigation(
        route = Graph.SEARCH,
        startDestination = Route.Search.route,
    ) {
        composable(route = Route.Search.route) {
            BackHandler {
                navigateToHome()
            }
            val keyword = stringResource(R.string.keyword)
            showBottomBar(true)
            SearchScreen(
                paddingValues = paddingValues,
                onSearchTriggered = {searchText ->
                    navController.navigate(
                        Route.Search.resultArgs(searchText)
                    )
                    onNextSearch(searchText)
                },
                searchViewModel = searchViewModel,
                onCategoryItemClicked = {
                    onCategoryItemClicked(keyword, it)
                },
                onCardClicked = onCardClicked
            )
        }
        composable(
            route = Route.Search.resultRoute(),
            arguments = listOf(navArgument(Constant.SEARCH_TEXT) { type = NavType.StringType }),
        ) {
            SearchResultScreen(
                paddingValues = paddingValues,
                onTeacherCardClicked = onTeacherCardClicked,
                onCardClicked = onCardClicked,
                onSearchTriggered = {searchText ->
                    onNextSearch(searchText)
                },
                searchViewModel = searchViewModel
            )
        }
    }
}