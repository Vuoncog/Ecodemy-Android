package com.kltn.ecodemy.navigation

import android.util.Log
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.kltn.ecodemy.constant.Constant.TEACHER_ID
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.argument
import com.kltn.ecodemy.ui.screens.teacher.TeacherScreen

fun NavGraphBuilder.teacherNavigation(
    navController: NavHostController
) {
    navigation(
        route = Graph.TEACHER.argument(type = TEACHER_ID),
        startDestination = Route.Teacher.route,
        arguments = listOf(navArgument(TEACHER_ID) { type = NavType.StringType }),
    ) {
        composable(
            route = Route.Teacher.route
        ) { entry ->
            val parentEntry =
                remember(entry) { navController.getBackStackEntry(Graph.TEACHER.argument(type = TEACHER_ID)) }
            val classId = parentEntry.arguments?.getString(TEACHER_ID)
            Log.d("NavigationTeacher", classId ?: "dont find")
            TeacherScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}