package com.stellerbyte.uptodo.screens.category

import androidx.compose.ui.graphics.Color
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.ui.theme.DesignBox
import com.stellerbyte.uptodo.ui.theme.GroceryColor
import com.stellerbyte.uptodo.ui.theme.HealthBox
import com.stellerbyte.uptodo.ui.theme.HomeBox
import com.stellerbyte.uptodo.ui.theme.MovieBox
import com.stellerbyte.uptodo.ui.theme.MusicColor
import com.stellerbyte.uptodo.ui.theme.SocialBox
import com.stellerbyte.uptodo.ui.theme.SportBox
import com.stellerbyte.uptodo.ui.theme.UniColor
import com.stellerbyte.uptodo.ui.theme.WorkColor

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

enum class Icons constructor(var icon: Int, var title : String, var color: Color){
    Work(R.drawable.work,"Work", WorkColor),
    University(R.drawable.university,"School", UniColor),
    Sport(R.drawable.sport,"Sport", SportBox),
    Music(R.drawable.music,"Music", MusicColor),
    Design(R.drawable.design,"Design", DesignBox),
    Home(R.drawable.home,"Home", HomeBox),
    Movie(R.drawable.movie,"Movie", MovieBox),
    Health(R.drawable.health,"Health", HealthBox),
    Social(R.drawable.social,"Social", SocialBox),
    Grocery(R.drawable.grocery_svg,"Grocery", GroceryColor)
}






