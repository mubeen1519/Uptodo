package com.example.uptodo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

object AppThemeTypography {
    val selectedFontFamily = mutableStateOf(latoFamily)
}


private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    secondary = BottomBarColor,
    tertiary = Pink80,
    background = Color.Black,
    onSurface = Color.White,
    surfaceVariant = DarkSurfaceVarient
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = Secondary,
    tertiary = Pink40,
    background = Color.White,
    onSurface = Color.Black,
    surfaceVariant = LightSurfaceVarient
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun UptodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

//    val typography = MaterialTheme.typography.copy(
//        bodySmall = MaterialTheme.typography.bodySmall.copy(
//            fontFamily = AppTheme.selectedFontFamily.value,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
//        ),
//        bodyLarge = MaterialTheme.typography.bodyLarge.copy(
//            fontFamily = AppTheme.selectedFontFamily.value,
//            fontWeight = FontWeight.Normal,
//            fontSize = 30.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
//        ),
//        titleLarge = MaterialTheme.typography.titleLarge.copy(
//            fontFamily = AppTheme.selectedFontFamily.value,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
//        ),
//        titleMedium = MaterialTheme.typography.titleMedium.copy(
//            fontFamily = AppTheme.selectedFontFamily.value,
//            fontWeight = FontWeight.Normal,
//            fontSize = 20.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
//        )
//    )
//


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )


}