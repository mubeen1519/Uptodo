package com.example.uptodo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.uptodo.screens.calender.CalenderScreen
import com.example.uptodo.screens.focus.FocusScreen
import com.example.uptodo.screens.home.HomeScreenContent
import com.example.uptodo.screens.profile.ProfileScreen
import com.example.uptodo.screens.settings.ThemeSetting

@Composable
fun HomeNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.Home,
        startDestination = BottomBar.Home.route
    ) {
        composable(BottomBar.Home.route,
        arguments = listOf(navArgument(Home_TODO_ID){defaultValue = DEFAULT_TODO_ID})
        ){

            HomeScreenContent(
                todoId = it.arguments?.getString(Home_TODO_ID) ?: DEFAULT_TODO_ID
            )
        }

        composable(BottomBar.Calender.route){
            CalenderScreen()
        }

        composable(BottomBar.Focus.route){
            FocusScreen()
        }
        composable(BottomBar.Profile.route){
            ProfileScreen(navHostController)
        }
        detailNavGraph(navHostController)
    }
}