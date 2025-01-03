package com.stellerbyte.uptodo.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stellerbyte.uptodo.components.CommonDialog
import com.stellerbyte.uptodo.components.RadioGroup
import com.stellerbyte.uptodo.components.RadioItems

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
            id = AppTheme.AUTO.ordinal,
            name = "Auto"
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose Your Theme", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
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

