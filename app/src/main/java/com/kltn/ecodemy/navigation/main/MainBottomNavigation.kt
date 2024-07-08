package com.kltn.ecodemy.navigation.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.TEACHER_ID
import com.kltn.ecodemy.constant.lessonIndex
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.navigation.addArgument
import com.kltn.ecodemy.domain.viewmodels.AccountViewModel
import com.kltn.ecodemy.domain.viewmodels.HomeViewModel
import com.kltn.ecodemy.domain.viewmodels.LearnViewModel
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.domain.viewmodels.WishlistViewModel
import com.kltn.ecodemy.navigation.accountNavigation
import com.kltn.ecodemy.navigation.homeNavigation
import com.kltn.ecodemy.navigation.learningNavigation
import com.kltn.ecodemy.navigation.searchNavigation
import com.kltn.ecodemy.navigation.wishlistNavigation

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainBottomNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    rootNavController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    learnViewModel: LearnViewModel,
    wishlistViewModel: WishlistViewModel,
    accountViewModel: AccountViewModel,
    startDestination: String = Graph.HOME,
    showBottomBar: (Boolean) -> Unit,
) {
    val navigateToHome = { navController.navigate(Graph.HOME) }

    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = startDestination
    ) {
        homeNavigation(
            paddingValues = paddingValues,
            onCardClicked = {
                rootNavController.navigate(Graph.COURSE.addArgument(arg = it, type = COURSE_ID))
            },
            onTeacherItemClicked = {
                rootNavController.navigate(Graph.TEACHER.addArgument(arg = it, type = TEACHER_ID))
            },
            onMessageClicked = {
                rootNavController.navigate(Graph.CHAT)
            },
            onNotificationClicked = {
                rootNavController.navigate(Route.Notification.route)
            },
            onCategoryItemClicked = { type, keyword ->
                rootNavController.navigate(
                    Route.Category.categoryArgs(
                        type = type,
                        keyword = keyword
                    )
                )
            },
            onRefreshClicked = {
                homeViewModel.refresh()
                searchViewModel.refresh()
                learnViewModel.refresh()
                wishlistViewModel.refresh()
                accountViewModel.refresh()
            },
            homeViewModel = homeViewModel,
            showBottomBar = showBottomBar,
        )
        searchNavigation(
            paddingValues = paddingValues,
            navController = navController,
            onTeacherCardClicked = {
                rootNavController.navigate(Graph.TEACHER.addArgument(arg = it, type = TEACHER_ID))
            },
            onCardClicked = {
                rootNavController.navigate(Graph.COURSE.addArgument(arg = it, type = COURSE_ID))
            },
            onNextSearch = {
                searchViewModel.fetchSearchResult(it)
            },
            onCategoryItemClicked = { type, keyword ->
                rootNavController.navigate(
                    Route.Category.categoryArgs(
                        type = type,
                        keyword = keyword
                    )
                )
            },
            searchViewModel = searchViewModel,
            showBottomBar = showBottomBar,
            navigateToHome = navigateToHome
        )

        learningNavigation(
            navigateToHome = navigateToHome,
            paddingValues = paddingValues,
            onLoginClicked = {
                rootNavController.navigate(Graph.AUTHENTICATION)
            },
            onCardClicked = {
                val splitter = it.split("-")
                val courseId = splitter.first()
                val sectionId = splitter[1].toInt()
                val lessonId = splitter[2].toInt()
                Route.Lesson.navigateLesson(
                    navController = rootNavController,
                    courseId = courseId,
                    sectionId = sectionId,
                    lessonId = (lessonId).lessonIndex(sectionId),
                )
            },
            showBottomBar = showBottomBar,
            learnViewModel = learnViewModel,
        )

        wishlistNavigation(
            navigateToHome = navigateToHome,
            paddingValues = paddingValues,
            onLoginClicked = {
                rootNavController.navigate(Graph.AUTHENTICATION)
            },
            onCardClicked = {
                rootNavController.navigate(Graph.COURSE.addArgument(arg = it, type = COURSE_ID))
            },
            wishlistViewModel = wishlistViewModel,
            showBottomBar = showBottomBar
        )

        accountNavigation(
            navigateToHome = navigateToHome,
            paddingValues = paddingValues,
            navController = navController,
            onLoginClicked = {
                rootNavController.navigate(Graph.AUTHENTICATION)
                searchViewModel.refresh()
            },
            onLogoutClicked = {
                rootNavController.navigate(
                    Graph.MAIN.addArgument(
                        Graph.ACCOUNT,
                        Constant.START_DESTINATION
                    )
                )
                searchViewModel.refresh()
            },
            accountViewModel = accountViewModel,
            showBottomBar = showBottomBar,
        )
    }
}
