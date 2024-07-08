package com.kltn.ecodemy.ui.teacherscreens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.navigation.main.TeacherMainBottomNavigation
import com.kltn.ecodemy.ui.components.KocoBottomAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeacherMainScreen(
    rootNavController: NavHostController,
    startDestination: String,
) {
    Log.d("StartDestination", startDestination)
    val items = listOf(Route.Home, Route.Account)
    val navController = rememberNavController()
    val showBottomBar = remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (showBottomBar.value) {
                KocoBottomAppBar(navController = navController, items = items)
            }
        },
    ) { paddingValues ->
        TeacherMainBottomNavigation(
            navController = navController,
            rootNavController = rootNavController,
            paddingValues = paddingValues,
            startDestination = startDestination,
            showBottomBar = {
                showBottomBar.value = it
            }
        )
    }
}