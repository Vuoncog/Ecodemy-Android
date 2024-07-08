package com.kltn.ecodemy.navigation.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.START_DESTINATION
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.argument
import com.kltn.ecodemy.ui.screens.main.MainScreen

@ExperimentalLayoutApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
fun NavGraphBuilder.mainNavigation(
    rootNavController: NavHostController,
) {
    navigation(
        route = Graph.MAIN.argument(START_DESTINATION),
        startDestination = "main",
        arguments = listOf(navArgument(START_DESTINATION) { type = NavType.StringType })
    ) {
        composable(route = "main") { entry ->
            val parentEntry =
                remember(entry) { rootNavController.getBackStackEntry(Graph.MAIN.argument(type = START_DESTINATION)) }
            val startDestination = parentEntry.arguments?.getString(START_DESTINATION) ?: Graph.HOME
            MainScreen(
                rootNavController = rootNavController,
                startDestination = startDestination
            )
        }
    }
}