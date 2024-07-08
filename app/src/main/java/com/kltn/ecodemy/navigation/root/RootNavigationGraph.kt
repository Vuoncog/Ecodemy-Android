package com.kltn.ecodemy.navigation.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kltn.ecodemy.constant.Constant.START_DESTINATION
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.navigation.authNavigation
import com.kltn.ecodemy.navigation.chatNavigation
import com.kltn.ecodemy.navigation.courseNavigation
import com.kltn.ecodemy.navigation.teacherNavigation
import com.kltn.ecodemy.ui.screens.category.CategoryScreen
import com.kltn.ecodemy.ui.screens.notification.NotificationScreen
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination,
    ) {
        mainNavigation(
            rootNavController = navController,
        )
        teacherMainNavigation(rootNavController = navController)
        courseNavigation(
            navController = navController,
        )
        chatNavigation(
            navController = navController,
        )
        teacherNavigation(
            navController = navController,
        )
        authNavigation(
            navController = navController,
        )

        composable(route = Route.Category.categoryRoute()) {
            CategoryScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                onItemClicked = {
                    navController.navigate(
                        Graph.COURSE.addArgument(
                            arg = it,
                            type = Route.Arg.COURSE_ID
                        )
                    )
                }
            )
        }

        composable(route = Route.Notification.route) {
            NotificationScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}