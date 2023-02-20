package com.example.uptodo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uptodo.screens.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraphBuilder(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Graph.Authentication, route = Graph.Root) {
        authNavGraph(navHostController)
        composable(route = Graph.Home){
            HomeScreen()
        }


    }


}

object Graph {
    const val Root = "root_graph"
    const val Authentication = "auth_graph"
    const val Home = "home_graph$STORE_TODO_ID"
    const val DETAILS = "details_graph"


}