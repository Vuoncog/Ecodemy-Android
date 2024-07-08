package com.kltn.ecodemy.navigation.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.argument
import com.kltn.ecodemy.ui.screens.main.MainScreen
import com.kltn.ecodemy.ui.teacherscreens.TeacherMainScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.teacherMainNavigation(
    rootNavController: NavHostController,
) {
    navigation(
        route = Graph.TEACHER_MAIN.argument(Constant.START_DESTINATION),
        startDestination = "main",
        arguments = listOf(navArgument(Constant.START_DESTINATION) { type = NavType.StringType })
    ) {
        composable(route = "main") { entry ->
            val parentEntry =
                remember(entry) { rootNavController.getBackStackEntry(Graph.TEACHER_MAIN.argument(type = Constant.START_DESTINATION)) }
            val startDestination = parentEntry.arguments?.getString(Constant.START_DESTINATION) ?: Graph.HOME
            TeacherMainScreen(
                rootNavController = rootNavController,
                startDestination = startDestination
            )
        }
    }
}