package com.example.uptodo.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DrawerLayout(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    onStateChange: (DrawerState) -> Unit,
    gestureEnabled: Boolean = true,
    drawerContent: @Composable ColumnScope.() -> Unit,
) {

//    ModalDrawer(
//        drawerContent = drawerContent,
//        drawerState = drawerState,
//        modifier = modifier,
//        gesturesEnabled = gestureEnabled,
//        drawerBackgroundColor = Color.Black
//    )


}


