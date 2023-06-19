package com.example.uptodo.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uptodo.components.BottomScreen
import com.example.uptodo.components.VectorIcon
import com.example.uptodo.navigation.BottomNavigationBar
import com.example.uptodo.navigation.HomeNavGraph
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
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
) {

    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                BottomScreen(sheetValue = state)
            }
        },
        sheetBackgroundColor = BottomBarColor,
        sheetShape = RoundedCornerShape(20.dp),
    ) {
        Scaffold(
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
            bottomBar = { BottomNavigationBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            if (state.isVisible) {
                                state.hide()
                            } else {
                                state.show()
                            }
                        }
                    },
                    containerColor = Purple40,
                    shape = CircleShape,
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                ) {
                    VectorIcon(imageVector = Icons.Default.Add, contentDescription = "Add todo")
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                HomeNavGraph(navHostController = navController)
            }
        }
    }
}






