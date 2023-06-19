package com.example.uptodo.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.uptodo.components.DrawableIcon

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val pages = listOf(
        BottomBar.Home,
        BottomBar.Calender,
        BottomBar.Focus,
        BottomBar.Profile,
        )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val bottomBarDestination = pages.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.height(85.dp),
            elevation = 3.dp
        ) {
            pages.forEach { screen ->
                AddItems(
                    screen = screen,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItems(
    screen: BottomBar,
    navController: NavHostController,
    currentDestination: NavDestination?,

    ) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    CompositionLocalProvider(androidx.compose.material.LocalContentColor provides Color.White) {
        BottomNavigationItem(
            selectedContentColor = MaterialTheme.colorScheme.onSurface,
            selected = isSelected,
            modifier = Modifier.padding(bottom = 10.dp),
            onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            },
            label = {
                Text(
                    text = screen.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            icon = {
                DrawableIcon(
                    painter = painterResource(id = if (isSelected) screen.icon_focused else screen.icon),
                    contentDescription = screen.title,
                    modifier = Modifier.size(25.dp),
                )
            },
        )
    }
}





