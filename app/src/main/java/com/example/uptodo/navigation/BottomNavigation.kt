package com.example.uptodo.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val pages = listOf(
        BottomBar.Home,
        BottomBar.Profile,
        BottomBar.Calender,
        BottomBar.Focus
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val bottomBarDestination = pages.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = BottomBarColor, modifier = Modifier.height(70.dp)
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
    BottomNavigationItem(
        selectedContentColor = Color.White,
        unselectedContentColor = Color.White,
        modifier = Modifier.padding(6.dp),
        selected = isSelected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }

        },
        label = {
            Text(text = screen.title, color = Color.White, fontSize = 12.sp)
        },
        icon = {
            DrawableIcon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.title,
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        },

        )
}





