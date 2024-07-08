package com.kltn.ecodemy.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.LearnViewModel
import com.kltn.ecodemy.ui.screens.learn.LearnContent
import com.kltn.ecodemy.ui.screens.search.SearchContent

fun NavGraphBuilder.learningNavigation(
    paddingValues: PaddingValues,
    learnViewModel: LearnViewModel,
    onCardClicked: (String) -> Unit,
    onLoginClicked: () -> Unit,
    navigateToHome: () -> Unit,
    showBottomBar: (Boolean) -> Unit,
) {
    navigation(
        route = Graph.LEARNING,
        startDestination = Route.Learn.route,
    ) {
        composable(route = Route.Learn.route) {
            BackHandler {
                navigateToHome()
            }
            showBottomBar(true)
            LearnContent(
                onCardClicked = onCardClicked,
                paddingValues = paddingValues,
                learnViewModel = learnViewModel,
                onLoginClicked = onLoginClicked
            )
        }
    }
}