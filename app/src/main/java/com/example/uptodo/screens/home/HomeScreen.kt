package com.example.uptodo.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uptodo.components.SheetLayout
import com.example.uptodo.components.VectorIcon
import com.example.uptodo.navigation.*
import com.example.uptodo.screens.category.BottomSheetType
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController(),viewModel: HomeViewModel = hiltViewModel()) {

    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Details.CategoryPages.route -> false
        else -> true
    }
    var currentBottomSheet: BottomSheetType? by remember {
        mutableStateOf(null)
    }
    val openSheet: (BottomSheetType) -> Unit = {
        coroutineScope.launch {
            currentBottomSheet = it
            if (state.isVisible) {
                state.hide()
            } else {
                state.show()
            }
        }
    }
    val todo by viewModel.todo

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                currentBottomSheet?.let {
                    SheetLayout(
                        bottomSheetType = it,
                        navController = navController,
                        state = state,
                        todoId = todo.id
                    )
                }
            }
        },
        sheetBackgroundColor = BottomBarColor,
        sheetShape = RoundedCornerShape(20.dp),
    ) {
        Scaffold(
            backgroundColor = Color.Black,
            bottomBar = { if (showBottomBar) BottomNavigationBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (showBottomBar) {
                            coroutineScope.launch {
                                currentBottomSheet = BottomSheetType.TYPE1
                                if (state.isVisible) {
                                    state.hide()
                                } else {
                                    state.show()
                                }
                            }
                        }
                    },
                    containerColor = if (showBottomBar) Purple40 else Color.Black,
                    shape = RoundedCornerShape(100.dp),
                    contentColor = if (showBottomBar) Color.White else Color.Black
                ) {
                    VectorIcon(imageVector = Icons.Default.Add, contentDescription = "Add todo")
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                HomeNavGraph(navHostController = navController, openSheet = openSheet)
            }
        }
    }
}






