package com.stellerbyte.uptodo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stellerbyte.uptodo.screens.authentication.CreateAccount
import com.stellerbyte.uptodo.screens.authentication.login.LoginPage
import com.stellerbyte.uptodo.screens.authentication.register.RegisterPage
import com.stellerbyte.uptodo.screens.splash.Pages
import com.stellerbyte.uptodo.screens.splash.Splash

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.Authentication,
        startDestination = Splash_Screen
    ) {
        composable(Splash_Screen) {
            Splash(navigate = { route -> navHostController.navigate(route) })
        }

        composable(Intro_Pages) {
            Pages(navigate = { route -> navHostController.navigate(route) })
        }

        composable(Create_Account) {
            CreateAccount(navController = navHostController)
        }

        composable(Login) {
            LoginPage(navigate = { route -> navHostController.navigate(route) })
        }

        composable(Registration) {
            RegisterPage(navigate = { route -> navHostController.navigate(route) })
        }

    }
}