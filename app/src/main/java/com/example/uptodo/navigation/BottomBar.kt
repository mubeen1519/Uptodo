package com.example.uptodo.navigation

import com.example.uptodo.R

sealed class BottomBar(var title: String, var icon: Int,var icon_focused : Int, var route: String) {
    object Home : BottomBar("Home", R.drawable.home_svgs,R.drawable.home_filled,com.example.uptodo.navigation.Home)
    object Calender : BottomBar("Calender", R.drawable.calendar_svg,R.drawable.calendar_filled, CalenderPage)
    object Focus : BottomBar("Focus", R.drawable.clock_svg,R.drawable.clock_filled, FocusPage)
    object Profile : BottomBar("Profile", R.drawable.user_svg,R.drawable.user_svg, ProfilePage)
}


