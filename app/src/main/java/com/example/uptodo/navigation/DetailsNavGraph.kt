package com.example.uptodo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.uptodo.screens.edittodo.EditTodoScreen

fun NavGraphBuilder.detailNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = EditTodoPage
    ) {
        composable(
            route ="$EditTodoPage$TASK_ID_ARG",
            arguments = listOf(navArgument(TASK_ID){ defaultValue = TASK_DEFAULT_ID})
        ) {
            EditTodoScreen(
                todoId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID,
                popUp = {navHostController.popBackStack()}
            )
        }
    }
}
