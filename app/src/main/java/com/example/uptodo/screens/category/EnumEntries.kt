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

enum class Colors constructor(val color : Color,val icon: Int) {
    Yellow(Yellows,R.drawable.baseline_check_24),
    Green(Color.Green,R.drawable.baseline_check_24),
    SkyBlue(com.example.uptodo.ui.theme.SkyBlue,R.drawable.baseline_check_24),
    OceanBlue(com.example.uptodo.ui.theme.OceanBlue,R.drawable.baseline_check_24),
    LightBlue(com.example.uptodo.ui.theme.LightBlue,R.drawable.baseline_check_24),
    Peach(HomeBox,R.drawable.baseline_check_24),
    DarkPurple(Margenda,R.drawable.baseline_check_24),
    DarkPink(com.example.uptodo.ui.theme.DarkPink,R.drawable.baseline_check_24)
}


enum class Icons constructor(var icon: Int, var title : String, var color: Color){
    Work(R.drawable.work,"Work", WorkColor),
    University(R.drawable.university,"School", UniColor),
    Sport(R.drawable.sport,"Sport", SportBox),
    Music(R.drawable.music,"Music", SocialBox),
    Design(R.drawable.design,"Design", DesignBox),
    Home(R.drawable.home,"Home", HomeBox),
    Movie(R.drawable.movie,"Movie", MovieBox),
    Health(R.drawable.health,"Health", HealthBox),
    Social(R.drawable.social,"Social", SocialBox),
    Grocery(R.drawable.grocery_svg,"Grocery", SportBox)
}

enum class BottomSheetType {
    TYPE1, TYPE2,
}





