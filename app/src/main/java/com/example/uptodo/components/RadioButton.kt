package com.example.uptodo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.UptodoTheme

data class RadioItems(
    var id: Int,
    var name: String,
)

@Composable
fun RadioGroup(
    items: Iterable<RadioItems>,
    selected: Int,
    onItemSelected: ((Int) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .selectableGroup()
        .background(BottomBarColor)) {
        items.forEach { item ->
            RadioGroupItems(
                items = item,
                selected = selected == item.id,
                onClick = { onItemSelected?.invoke(item.id) },

                )
        }
    }
}

@Composable
private fun RadioGroupItems(
    items: RadioItems,
    selected: Boolean,
    onClick: ((Int) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = { onClick?.invoke(items.id) },
                role = Role.RadioButton
            )
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 8.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = modifier.width(5.dp))
        Text(
            text = items.name,
            color = Color.White
        )
    }
}

private val radioItems = listOf(
    RadioItems(
        id = 1,
        name = "Light Theme",
    ),
    RadioItems(
        id = 2,
        name = "Dark Theme",
    ),
    RadioItems(
        id = 3,
        name = "Auto Theme",
    ),
)

@Preview
@Composable
fun items() {
    UptodoTheme {
        var selected by remember {
            mutableStateOf(2)
        }
        RadioGroup(
            items = radioItems,
            selected = selected,
            onItemSelected = { id -> selected = id })
    }
}

//@Preview
//@Composable
//fun radio(){
//    UptodoTheme {
//        RadioGroupItems(items = RadioItems(
//            id = 1,
//            name = "Dark Theme"
//        ), selected = true, onClick = null )
//    }
//}