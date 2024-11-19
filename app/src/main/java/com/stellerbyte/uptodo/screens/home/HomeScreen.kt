package com.stellerbyte.uptodo.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stellerbyte.uptodo.components.BottomScreen
import com.stellerbyte.uptodo.components.VectorIcon
import com.stellerbyte.uptodo.navigation.BottomBar
import com.stellerbyte.uptodo.navigation.BottomNavigationBar
import com.stellerbyte.uptodo.navigation.HomeNavGraph
import com.stellerbyte.uptodo.navigation.TASK_DEFAULT_ID
import com.stellerbyte.uptodo.navigation.TASK_ID
import com.stellerbyte.uptodo.ui.theme.BottomBarColor
import com.stellerbyte.uptodo.ui.theme.Purple40
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
    todoId : String,
    state : ModalBottomSheetState
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
 // Handle the back press action on the Home screen
    BackHandler {
        if (navController.currentBackStackEntry?.destination?.route == BottomBar.Home.route) {
            // Exit the app if on the Home screen
            (context as? Activity)?.finish()
        } else {
            navController.popBackStack()
        }
    }
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
            HomeScreenContent(
                openSheet = { route -> navController.navigate(route) },
                todoId = todoId,
                navController = navController
            )
        }
    }







