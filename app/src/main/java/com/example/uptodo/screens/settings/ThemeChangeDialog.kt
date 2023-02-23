package com.example.uptodo.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uptodo.components.CommonDialog
import com.example.uptodo.components.RadioGroup
import com.example.uptodo.components.RadioItems
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.UptodoTheme

@Composable
fun ChangeThemeDialog(dialogState: MutableState<Boolean>, userSetting: ThemeSetting) {
    CommonDialog(state = dialogState) {
        val theme = userSetting.themeFlow.collectAsState()
        ThemeContent(selectedTheme = theme.value, onItemSelect = { themes ->
            userSetting.theme = themes
        })
    }
}


@Composable
private fun ThemeContent(
    selectedTheme: AppTheme,
    onItemSelect: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeItems = listOf(
        RadioItems(
            id = AppTheme.DAY.ordinal,
            name = "Light Theme"
        ),
        RadioItems(
            id = AppTheme.NIGHT.ordinal,
            name = "Night Theme"
        ),
        RadioItems(
            id = AppTheme.ORANGE.ordinal,
            name = "Orange Theme"
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BottomBarColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose Your Theme", textAlign = TextAlign.Center, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(10.dp))
        RadioGroup(
            items = themeItems,
            selected = selectedTheme.ordinal,
            onItemSelected = { id -> onItemSelect(AppTheme.fromOrdinal(id)) },
        )
    }
}

@Preview
@Composable
fun perv(){
    UptodoTheme {
        var theme by remember {
            mutableStateOf(AppTheme.DAY)
        }
        ThemeContent(selectedTheme = theme, onItemSelect ={value -> theme = value})
    }
}