package com.example.uptodo.screens.category

import androidx.compose.ui.graphics.Color
import com.example.uptodo.R
import com.example.uptodo.ui.theme.*

enum class Priority constructor(val value : Int,val icon: Int){
    Priority1(1,R.drawable.priority),
    Priority2(2,R.drawable.priority),
    Priority3(3,R.drawable.priority),
    Priority4(4,R.drawable.priority),
    Priority5(5,R.drawable.priority),
    Priority6(6,R.drawable.priority),
    Priority7(7,R.drawable.priority),
    Priority8(8,R.drawable.priority),
    Priority9(9,R.drawable.priority),
    Priority10(10,R.drawable.priority),
}

enum class Colors constructor(val color : Color) {
    Yellow(Yellows),
    Green(Color.Green),
    SkyBlue(com.example.uptodo.ui.theme.SkyBlue),
    OceanBlue(com.example.uptodo.ui.theme.OceanBlue),
    LightBlue(com.example.uptodo.ui.theme.LightBlue),
    Peach(HomeBox),
    DarkPurple(Margenda),
    DarkPink(com.example.uptodo.ui.theme.DarkPink)
}


enum class Icons constructor(val icon: Int,val title : String,val color: Color){
    Work(R.drawable.work,"Work", WorkColor),
    University(R.drawable.university,"University", UniColor),
    Sport(R.drawable.sport,"Sport", SportBox),
    Music(R.drawable.music,"Music", Margenda),
    Design(R.drawable.design,"Design", DesignBox),
    Home(R.drawable.home,"Home", HomeBox),
    Movie(R.drawable.movie,"Movie", MovieBox),
    Health(R.drawable.health,"Health", HealthBox),
    Social(R.drawable.social,"Social", SocialBox),
    Grocery(R.drawable.grocery,"Grocery", SportBox)
}



