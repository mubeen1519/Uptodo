package com.example.uptodo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.uptodo.navigation.RootNavigationGraphBuilder
import com.example.uptodo.screens.settings.AppTheme
import com.example.uptodo.screens.settings.ThemeSetting
import com.example.uptodo.ui.theme.AppThemeTypography
import com.example.uptodo.ui.theme.Typography
import com.example.uptodo.ui.theme.UptodoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeSetting: ThemeSetting

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme = themeSetting.themeFlow.collectAsState()
            val useDarkColors = when (theme.value) {
                AppTheme.DAY -> false
                AppTheme.AUTO -> isSystemInDarkTheme()
                AppTheme.NIGHT -> true
            }



            UptodoTheme(darkTheme = useDarkColors) {
                MaterialTheme(
                    typography = Typography.copy(
                        bodyLarge = Typography.bodyLarge.copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        titleLarge = Typography.titleLarge
                            .copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        labelSmall = Typography.labelSmall.copy(fontFamily = AppThemeTypography.selectedFontFamily.value)
                    ),

                    ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreenView()
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    RootNavigationGraphBuilder(navHostController = navController)

}