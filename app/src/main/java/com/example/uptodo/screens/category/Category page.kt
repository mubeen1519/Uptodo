package com.example.uptodo.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.components.categories.CategoryDialog
import com.example.uptodo.components.categories.LibraryIcon
import com.example.uptodo.navigation.Home
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40

@Composable
fun CategoryPage(navigate: (String) -> Unit) {

    val iconLibraryState : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val selectedValue by remember {
        mutableStateOf(0)
    }

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(id = R.string.new_category),
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))
            InputField(
                placeholderText = "Category name",
                onFieldChange = {},
                label = "Category name:"
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.category_icon), color = Color.White)

            Spacer(modifier = Modifier.height(15.dp))
            if (iconLibraryState.value) {
                LibraryIcon(
                    state = iconLibraryState,

                )
            }
            Button(
                onClick = {
                          iconLibraryState.value = true
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = BottomBarColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(width = 200.dp, height = 40.dp)
            ) {
                Text(text = stringResource(id = R.string.choose_icon), color = Color.White)
            }

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.category_color), color = Color.White)

            Spacer(modifier = Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EnumColors(itemWidth = 30.dp, onItemSelection = { Colors.values() })
            }

            Spacer(modifier = Modifier.height(40.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = Purple40,
                        modifier = Modifier.clickable { navigate(Home) })

                    Button(
                        onClick = {},
                        modifier = Modifier.size(width = 150.dp, height = 40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple40,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Create Category", color = Color.White)
                    }
                }
            }
        }
    }

@Composable
fun EnumColors(
    itemWidth: Dp,
    defaultSelectedItemIndex: Int = 0,
    onItemSelection: (selectedValue: Int) -> Unit,
) {
    val selectedIndex = remember {
        mutableStateOf(defaultSelectedItemIndex)
    }

    Colors.values().forEachIndexed { index, item ->
        Box(
            modifier = Modifier
                .size(itemWidth)
                .clip(CircleShape)
                .background(item.color)
                .clickable {
                    selectedIndex.value = index
                    onItemSelection(selectedIndex.value)
                })
    }
}