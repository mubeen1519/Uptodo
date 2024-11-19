package com.stellerbyte.uptodo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stellerbyte.uptodo.screens.home.HomeScreen

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraphBuilder(navHostController: NavHostController,state : ModalBottomSheetState) {
    NavHost(navController = navHostController, startDestination = Graph.Authentication, route = Graph.Root) {
        authNavGraph(navHostController)
        HomeNavGraph(navHostController = navHostController,state)
    }


}

object Graph {
    const val Root = "root_graph"
    const val Authentication = "auth_graph"
    const val Home = "home_graph"
    const val DETAILS = "details_graph"


}