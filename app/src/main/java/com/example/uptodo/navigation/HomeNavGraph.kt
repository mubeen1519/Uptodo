package com.example.uptodo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uptodo.screens.calender.CalenderScreen
import com.example.uptodo.screens.focus.FocusScreen
import com.example.uptodo.screens.home.HomeScreenContent
import com.example.uptodo.screens.profile.ProfileScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.Home,
        startDestination = BottomBar.Home.route
    ) {
        composable(BottomBar.Home.route) {
            HomeScreenContent(
                openSheet = { route -> navHostController.navigate(route) },
                navController = navHostController
            )
        }

        composable(BottomBar.Calender.route) {
            CalenderScreen(openSheet = { route -> navHostController.navigate(route) })
        }

        composable(BottomBar.Focus.route) {
            FocusScreen()
        }
        composable(BottomBar.Profile.route) {
            ProfileScreen(navigate = { route -> navHostController.navigate(route) })
        }

        detailNavGraph(navHostController)
    }
}