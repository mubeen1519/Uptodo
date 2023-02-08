package com.example.uptodo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.uptodo.R
import com.example.uptodo.navigation.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    onIconClick : () -> Unit
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == null || currentRoute == BottomBar.Home.route) {
        return
    }


   androidx.compose.material.TopAppBar(
       title = { Text(text = "Home", textAlign = TextAlign.Center)},
       navigationIcon = {
           IconButton(onClick = { onIconClick()}) {
               DrawableIcon(
                   painter = painterResource(id = R.drawable.dropdown),
                   contentDescription = "Drop Down",
                   modifier = Modifier.size(40.dp)
               )
           }
       },
       contentColor = Color.White,
       actions = {

       },
       backgroundColor = Color.Black
       )
}