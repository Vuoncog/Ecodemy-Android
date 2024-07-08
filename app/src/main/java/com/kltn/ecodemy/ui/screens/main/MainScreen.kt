package com.kltn.ecodemy.ui.screens.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.domain.viewmodels.AccountViewModel
import com.kltn.ecodemy.domain.viewmodels.HomeViewModel
import com.kltn.ecodemy.domain.viewmodels.LearnViewModel
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.domain.viewmodels.WishlistViewModel
import com.kltn.ecodemy.navigation.main.MainBottomNavigation
import com.kltn.ecodemy.ui.components.KocoBottomAppBar

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    startDestination: String,
) {
    val navController = rememberNavController()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val learnViewModel = hiltViewModel<LearnViewModel>()
    val wishlistViewModel = hiltViewModel<WishlistViewModel>()
    val accountViewModel = hiltViewModel<AccountViewModel>()
    val showBottomBar = remember {
        mutableStateOf(false)
    }
    val items = listOf(Route.Home, Route.Search, Route.Learn, Route.Wishlist, Route.Account)
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (showBottomBar.value) {
                KocoBottomAppBar(navController = navController, items = items)
            }
        },
    ) { paddingValues ->
        MainBottomNavigation(
            navController = navController,
            rootNavController = rootNavController,
            paddingValues = paddingValues,
            homeViewModel = homeViewModel,
            searchViewModel = searchViewModel,
            learnViewModel = learnViewModel,
            wishlistViewModel = wishlistViewModel,
            accountViewModel = accountViewModel,
            startDestination = startDestination,
            showBottomBar = {
                showBottomBar.value = it
            }
        )
    }
}