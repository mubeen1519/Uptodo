package com.example.uptodo.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.BottomBarColor
import kotlinx.coroutines.launch

sealed class BottomBar(var title: String, var icon: Int, var route: String) {
    object Home : BottomBar("Home", R.drawable.home_icon, "${com.example.uptodo.navigation.Home}$STORE_TODO_ID")
    object Calender : BottomBar("Calender", R.drawable.calendar, CalenderPage)
    object Focus : BottomBar("Focus", R.drawable.clock, FocusPage)
    object Profile : BottomBar("Profile", R.drawable.user, ProfilePage)
}


