package com.example.uptodo.components.categories

import androidx.compose.ui.graphics.Color

data class CategoryData(
    var icon : Int,
    val title : String,
    val color : Color
)

data class PriorityData(
    val icon: Int,
    val title: String
)
