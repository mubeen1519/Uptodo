package com.example.uptodo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.uptodo.R

// Set of Material typography styles to start with
val latoFamily = FontFamily(
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_light, weight = FontWeight.Light),
)

val quickSandFamily = FontFamily(
    Font(R.font.quicksand_light, weight =  FontWeight.Light),
    Font(R.font.quicksand_regular, weight =  FontWeight.Normal),
    Font(R.font.quicksand_bold, weight = FontWeight.Bold),
    Font(R.font.quicksand_medium, weight = FontWeight.Medium)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = latoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = latoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = latoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)


