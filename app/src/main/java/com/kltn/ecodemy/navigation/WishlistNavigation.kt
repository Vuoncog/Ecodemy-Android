package com.kltn.ecodemy.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kltn.ecodemy.data.navigation.Graph
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.HomeViewModel
import com.kltn.ecodemy.domain.viewmodels.WishlistViewModel
import com.kltn.ecodemy.ui.screens.home.HomeContent
import com.kltn.ecodemy.ui.screens.wishlist.WishlistContent
import kotlinx.coroutines.runBlocking

fun NavGraphBuilder.wishlistNavigation(
    paddingValues: PaddingValues,
    wishlistViewModel: WishlistViewModel,
    onCardClicked: (String) -> Unit,
    onLoginClicked: () -> Unit,
    navigateToHome: () -> Unit,
    showBottomBar: (Boolean) -> Unit,
) {
    navigation(
        route = Graph.WISHLIST,
        startDestination = Route.Wishlist.route,
    ) {
        composable(route = Route.Wishlist.route) {
            BackHandler {
                navigateToHome()
            }
            showBottomBar(true)
            WishlistContent(
                onCardClicked = onCardClicked,
                paddingValues = paddingValues,
                wishlistViewModel = wishlistViewModel,
                onLoginClicked = onLoginClicked
            )
        }
    }
}