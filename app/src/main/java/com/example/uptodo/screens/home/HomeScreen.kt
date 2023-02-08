package com.example.uptodo.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uptodo.components.VectorIcon
import com.example.uptodo.navigation.BottomNavigationBar
import com.example.uptodo.navigation.HomeNavGraph
import com.example.uptodo.navigation.ModalBottomSheet
import com.example.uptodo.ui.theme.Purple40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    Scaffold(
        backgroundColor = Color.Black,
        bottomBar = {BottomNavigationBar(navController = navController)},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    if (state.isVisible) {
                        state.hide()
                    } else {
                        state.show()
                    }
                }
            }, containerColor = Purple40, shape = RoundedCornerShape(100.dp)) {
                VectorIcon(imageVector = Icons.Default.Add, contentDescription = "Add todo")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) {
        HomeNavGraph(navHostController = navController)
        ModalBottomSheet(navController = navController, sheetValue = state)
    }
}



