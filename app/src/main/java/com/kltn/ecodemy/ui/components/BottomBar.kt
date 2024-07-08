package com.kltn.ecodemy.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.topBorder
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

private val BOTTOM_ITEM_SPACE_BETWEEN = Arrangement.spacedBy(2.dp)
private val BOTTOM_BAR_HORIZONTAL_PADDING = PaddingValues(horizontal = 12.dp)

@Composable
fun KocoBottomAppBar(
    navController: NavHostController,
    items: List<Route>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            .topBorder(
                strokeWidth = 1.dp,
                color = Neutral3
            )
            .background(color = Color.White)
            .padding(BOTTOM_BAR_HORIZONTAL_PADDING)
    ) {
        items.forEach { item ->
            BottomItem(
                modifier = Modifier.weight(1f),
                screen = item,
                navController = navController,
                currentDestination = currentDestination
            )
        }
    }
}

@Composable
fun BottomItem(
    modifier: Modifier = Modifier,
    screen: Route,
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    val isSelected = currentDestination?.hierarchy?.any {
        val route = it.route?.split("/")?.first() ?: "home"
        route == screen.route
    } == true

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.White)
            .clickable {
                if (!isSelected) {
                    navController.navigate(route = screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
            .padding(
                top = 8.dp,
                bottom = 4.dp
            )
            .then(modifier),
        verticalArrangement = BOTTOM_ITEM_SPACE_BETWEEN
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = screen.icon
            ),
            contentDescription = "Bottom Item Icon",
            tint = if (isSelected) Primary1 else Neutral3
        )
        Text(
            text = stringResource(id = screen.title),
            style = Nunito.Subtitle2.textStyle,
            color = if (isSelected) Primary1 else Neutral3
        )
    }
}