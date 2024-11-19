package com.stellerbyte.uptodo.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.stellerbyte.uptodo.screens.authentication.deleteAccount.DeleteUserScreen
import com.stellerbyte.uptodo.screens.calender.CalenderScreen
import com.stellerbyte.uptodo.screens.focus.FocusScreen
import com.stellerbyte.uptodo.screens.home.HomeScreen
import com.stellerbyte.uptodo.screens.profile.AboutUsScreen
import com.stellerbyte.uptodo.screens.profile.ProfileScreen


@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.HomeNavGraph(
    navHostController: NavHostController,
    state: ModalBottomSheetState
) {
    navigation(
        route = Graph.Home,
        startDestination = BottomBar.Home.route
    ) {
        composable(
            route = BottomBar.Home.route,
            arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
        ) {
            HomeScreen(
                todoId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID,
                navController = navHostController,
                state = state
            )
        }

        composable(BottomBar.Calender.route) {
            CalenderScreen(openSheet = { route -> navHostController.navigate(route) })
        }

        composable(BottomBar.Focus.route) {
            FocusScreen()
        }
        composable(BottomBar.Profile.route) {
            ProfileScreen(navigate = { route ->
                navHostController.navigate(route) {
                    popUpTo(Graph.Home) {
                        inclusive = true
                    }
                }
            })
        }

        composable(DeleteScreen) {
            DeleteUserScreen(navigate = { route -> navHostController.navigate(route) })
        }

        composable(ABOUT_US_SCREEN) {
            AboutUsScreen(navHostController = navHostController)
        }

        detailNavGraph(navHostController)
    }
}