package com.example.uptodo.navigation

import com.example.uptodo.R

sealed class BottomBar(var title: String, var icon : Int , var route : String){
    object Home : BottomBar("Home",R.drawable.home_icon, com.example.uptodo.navigation.Home)
    object Calender : BottomBar("Calender",R.drawable.calendar, CalenderPage)
    object Focus : BottomBar("Focus",R.drawable.clock, FocusPage)
    object Profile : BottomBar("Profile",R.drawable.user, ProfilePage)
}