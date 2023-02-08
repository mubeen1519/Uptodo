package com.example.uptodo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.uptodo.authentication.CreateAccount
import com.example.uptodo.authentication.LoginPage
import com.example.uptodo.authentication.register.RegisterPage
import com.example.uptodo.splash.Pages
import com.example.uptodo.splash.Splash

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController){
    navigation(
        route = Graph.Authentication,
        startDestination = Splash_Screen
    ){
        composable(Splash_Screen){
            Splash(navigate = {route -> navHostController.navigate(route)} )
        }

        composable(Intro_Pages){
            Pages(navigate = {route -> navHostController.navigate(route)})
        }

        composable(Create_Account){
            CreateAccount(navigate = {route -> navHostController.navigate((route))})
        }

        composable(Login){
            LoginPage(navigate = {route -> navHostController.navigate(route)})
        }

        composable(Registration){
            RegisterPage(navigate = {route -> navHostController.navigate(route)})
        }

    }
}