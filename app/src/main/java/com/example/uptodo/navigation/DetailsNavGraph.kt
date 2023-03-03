package com.example.uptodo.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.uptodo.screens.category.CategoryPage
import com.example.uptodo.services.implementation.TODOItem

fun NavGraphBuilder.detailNavGraph(navHostController: NavHostController){
navigation(
    route = Graph.DETAILS,
    startDestination = Details.CategoryPages.route
){
    composable(Details.CategoryPages.route){
        CategoryPage( navigate = {route -> navHostController.navigate(route)})
    }
}
}

sealed class Details(val route : String){
    object CategoryPages : Details("CategoryPage")
}