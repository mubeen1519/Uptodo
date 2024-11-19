package com.stellerbyte.uptodo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stellerbyte.uptodo.components.VectorIcon
import com.stellerbyte.uptodo.navigation.BottomBar
import com.stellerbyte.uptodo.navigation.BottomNavigationBar
import com.stellerbyte.uptodo.navigation.RootNavigationGraphBuilder
import com.stellerbyte.uptodo.screens.settings.AppTheme
import com.stellerbyte.uptodo.screens.settings.ThemeSetting
import com.stellerbyte.uptodo.services.SharedPreferencesUtil
import com.stellerbyte.uptodo.ui.theme.AppThemeTypography
import com.stellerbyte.uptodo.ui.theme.Purple40
import com.stellerbyte.uptodo.ui.theme.Typography
import com.stellerbyte.uptodo.ui.theme.UptodoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeSetting: ThemeSetting


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedPreferencesUtil.init(this)
            val theme = themeSetting.themeFlow.collectAsState()
            val useDarkColors = when (theme.value) {
                AppTheme.DAY -> true
                AppTheme.AUTO -> isSystemInDarkTheme()
                AppTheme.NIGHT -> false
            }
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    // permission granted
                } else {
                    // permission denied, but should I show a rationale?
                }
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // permission granted
            } else {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            UptodoTheme(darkTheme = useDarkColors) {
                MaterialTheme(
                    typography = Typography.copy(
                        bodyLarge = Typography.bodyLarge.copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        bodySmall = Typography.bodySmall.copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        titleLarge = Typography.titleLarge
                            .copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        titleMedium = Typography.titleMedium.copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        labelSmall = Typography.labelSmall.copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        titleSmall = Typography.titleSmall.copy(fontFamily = AppThemeTypography.selectedFontFamily.value),
                        headlineSmall = Typography.headlineSmall.copy(fontFamily = AppThemeTypography.selectedFontFamily.value)
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

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val state =
        androidx.compose.material.rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val floatingButtonState = rememberSaveable {
        (mutableStateOf(true))
    }
    when (navBackStackEntry?.destination?.route) {
        BottomBar.Home.route -> {
            bottomBarState.value = true
            floatingButtonState.value = true
        }

        BottomBar.Profile.route -> {
            bottomBarState.value = true
            floatingButtonState.value = true
        }

        BottomBar.Focus.route -> {
            bottomBarState.value = true
            floatingButtonState.value = true
        }

        BottomBar.Calender.route -> {
            bottomBarState.value = true
            floatingButtonState.value = true
        }

        else -> {
            bottomBarState.value = false
            floatingButtonState.value = false
        }
    }
    androidx.compose.material.Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (bottomBarState.value) {
                BottomNavigationBar(navController = navController)
            }
        },
        floatingActionButton = {
            if(floatingButtonState.value) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            if (state.isVisible) {
                                state.hide()
                            } else {
                                state.show()
                            }
                        }
                    },
                    containerColor = Purple40,
                    shape = CircleShape,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    VectorIcon(imageVector = Icons.Default.Add, contentDescription = "Add todo")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            RootNavigationGraphBuilder(navHostController = navController, state = state)
        }
    }
}