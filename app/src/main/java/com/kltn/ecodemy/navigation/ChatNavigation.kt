package com.kltn.ecodemy.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.ChatDetailViewModel
import com.kltn.ecodemy.ui.screens.chat.ChatListScreen
import com.kltn.ecodemy.ui.screens.chat.ChatScreen
import com.kltn.ecodemy.ui.screens.chat.MemberScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.chatNavigation(
    navController: NavHostController,
) {
    navigation(
        route = Graph.CHAT,
        startDestination = Route.Chat.chatListRoute(),
    ) {
        lateinit var chatDetailViewModel: ChatDetailViewModel
        composable(
            route = Route.Chat.chatListRoute()
        ) {
            ChatListScreen(
                onBackClicked = { navController.popBackStack() },
                onItemClicked = { chatTitle, imageUrl ->
                    val encode = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate(Route.Chat.messagesArgument(chatTitle, encode))
                }
            )
        }

        composable(
            route = Route.Chat.messagesRoute(),
            arguments = listOf(
                navArgument(Route.Chat.CHAT_TITLE) { type = NavType.StringType },
                navArgument(Route.Chat.IMAGE_URL) { type = NavType.StringType }
            )
        ) {
            chatDetailViewModel = hiltViewModel<ChatDetailViewModel>()
            ChatScreen(
                chatDetailViewModel = chatDetailViewModel,
                onBackClicked = {
                    navController.popBackStack()
                },
                onListClicked = {
                    navController.navigate(route = Route.Chat.memberRoute())
                }
            )
        }

        composable(
            route = Route.Chat.memberRoute(),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            MemberScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                chatDetailViewModel = chatDetailViewModel
            )
        }
    }
}

private fun extractImagePath(fullImageUrl: String): String {
    val chunks = fullImageUrl.split("%2F")
    val lastChunk = chunks.last()
    return lastChunk.split("?").first()
}