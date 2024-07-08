package com.kltn.ecodemy.navigation.teacher

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.TeacherHomeViewModel
import com.kltn.ecodemy.ui.teacherscreens.home.TeacherHomeScreen

fun NavGraphBuilder.teacherHomeNavigation(
    paddingValues: PaddingValues,
    teacherHomeViewModel: TeacherHomeViewModel,
    showBottomBar: (Boolean) -> Unit,
    onCardClicked: (String) -> Unit,
    onMessageClicked: () -> Unit,
) {
    navigation(
        route = Graph.HOME,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            showBottomBar(true)
            TeacherHomeScreen(
                paddingValues = paddingValues,
                teacherHomeViewModel = teacherHomeViewModel,
                onCardClicked = onCardClicked,
                onMessageClicked = onMessageClicked
            )
        }
    }
}