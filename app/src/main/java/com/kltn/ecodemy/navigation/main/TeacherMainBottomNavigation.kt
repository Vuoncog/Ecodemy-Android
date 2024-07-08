package com.kltn.ecodemy.navigation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.data.navigation.argument
import com.kltn.ecodemy.domain.viewmodels.AccountViewModel
import com.kltn.ecodemy.domain.viewmodels.TeacherHomeViewModel
import com.kltn.ecodemy.navigation.accountNavigation
import com.kltn.ecodemy.navigation.teacher.teacherHomeNavigation
import com.kltn.ecodemy.ui.teacherscreens.course.CourseInformationScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeacherMainBottomNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    rootNavController: NavHostController,
    startDestination: String = Graph.HOME,
    showBottomBar: (Boolean) -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel(),
    teacherHomeViewModel: TeacherHomeViewModel = hiltViewModel()
) {
    val navigateToHome = { navController.navigate(Graph.HOME) }

    NavHost(
        navController = navController,
        route = Graph.TEACHER_MAIN,
        startDestination = startDestination
    ) {
        teacherHomeNavigation(
            paddingValues = paddingValues,
            onCardClicked = {
                navController.navigate(Route.Course.route.addArgument(it, COURSE_ID))
            },
            showBottomBar = showBottomBar,
            teacherHomeViewModel = teacherHomeViewModel,
            onMessageClicked = {
                rootNavController.navigate(Graph.CHAT)
            }
        )

        accountNavigation(
            paddingValues = paddingValues,
            navController = navController,
            onLoginClicked = {
                rootNavController.navigate(Graph.AUTHENTICATION)
            },
            onLogoutClicked = {
                rootNavController.navigate(
                    Graph.MAIN.addArgument(
                        Graph.ACCOUNT,
                        Constant.START_DESTINATION
                    )
                )
            },
            accountViewModel = accountViewModel,
            showBottomBar = showBottomBar,
            navigateToHome = navigateToHome
        )

        composable(route = Route.Course.route.argument(COURSE_ID),
            arguments = listOf(
                navArgument(COURSE_ID) { type = NavType.StringType }
            )
        ) {
            showBottomBar(false)
            CourseInformationScreen(onBackClicked = { navController.popBackStack() })
        }
    }
}