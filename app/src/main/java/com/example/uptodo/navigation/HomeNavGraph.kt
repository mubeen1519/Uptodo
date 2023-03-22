package com.example.uptodo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uptodo.screens.calender.CalenderScreen
import com.example.uptodo.screens.category.BottomSheetType
import com.example.uptodo.screens.focus.FocusScreen
import com.example.uptodo.screens.home.HomeScreenContent
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.screens.profile.ProfileScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeNavGraph(navHostController: NavHostController,openSheet : (BottomSheetType) -> Unit,viewModel: HomeViewModel = hiltViewModel()) {
    NavHost(
        navController = navHostController,
        route = Graph.Home,
        startDestination = BottomBar.Home.route
    ) {
        composable(
            BottomBar.Home.route,
        ) {
            val todo by viewModel.todo
            HomeScreenContent(
                todoId = todo.id,
                openSheet = openSheet,
                navController = navHostController
            )
        }

        composable(BottomBar.Calender.route) {
            CalenderScreen(openSheet = openSheet)
        }

        composable(BottomBar.Focus.route) {
            FocusScreen()
        }
        composable(BottomBar.Profile.route) {
            ProfileScreen(navigate = {route -> navHostController.navigate(route)})
        }
        detailNavGraph(navHostController)
    }
}