package com.kltn.ecodemy.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kltn.ecodemy.R
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.HomeViewModel
import com.kltn.ecodemy.ui.screens.home.HomeContent

@ExperimentalFoundationApi
fun NavGraphBuilder.homeNavigation(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    onCardClicked: (String) -> Unit,
    onTeacherItemClicked: (String) -> Unit,
    onMessageClicked: () -> Unit,
    onRefreshClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onCategoryItemClicked: (String, String) -> Unit,
    showBottomBar: (Boolean) -> Unit,
) {
    navigation(
        route = Graph.HOME,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            val category = stringResource(R.string.category)
            HomeContent(
                onCardClicked = onCardClicked,
                paddingValues = paddingValues,
                homeViewModel = homeViewModel,
                onTeacherItemClicked = onTeacherItemClicked,
                onMessageClicked = onMessageClicked,
                onNotificationClicked = onNotificationClicked,
                showBottomBar = showBottomBar,
                onCategoryItemClicked = { onCategoryItemClicked(category, it) },
                onRefreshClicked = onRefreshClicked
            )
        }
    }
}