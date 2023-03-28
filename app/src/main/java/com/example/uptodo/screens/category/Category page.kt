package com.example.uptodo.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.components.categories.LibraryIcon
import com.example.uptodo.navigation.Home
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.Purple40

@Composable
fun CategoryPage(navigate: (String) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {

    val iconLibraryState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val selectedValue = remember {
        mutableStateOf(0)
    }

    val newValues = remember {
        mutableStateOf("")
    }


    Column(modifier = Modifier.padding(10.dp)) {
        Icons.values().forEachIndexed { index, icons ->
            Text(
                text = stringResource(id = R.string.new_category),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(15.dp))

            InputField(
                value = icons.title.replace(icons.title, newValues.value, ignoreCase = true),
                placeholderText = "Category name",
                onFieldChange = {
                    newValues.value = it
                },
                label = "Category name:"
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.category_icon),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(15.dp))
            if (iconLibraryState.value) {
                LibraryIcon(
                    state = iconLibraryState,
                    selectedItem = { selectedValue.value = it }
                )
            }
            Button(
                onClick = {
                    iconLibraryState.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                IconButton(onClick = {
                    iconLibraryState.value = false
                    selectedValue.value = index
                }) {
                    DrawableIcon(
                        painter =
                        painterResource(
                            id = when (selectedValue.value) {
                                Icons.Grocery.icon -> icons.icon
                                Icons.University.icon -> icons.icon
                                Icons.Work.icon -> icons.icon
                                Icons.Sport.icon -> icons.icon
                                Icons.Social.icon -> icons.icon
                                Icons.Music.icon -> icons.icon
                                Icons.Home.icon -> icons.icon
                                Icons.Health.icon -> icons.icon
                                Icons.Design.icon -> icons.icon
                                Icons.Movie.icon -> icons.icon
                                else -> Icons.Home.icon
                            }
                        ), contentDescription = "icons",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.category_color),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EnumColors(itemWidth = 30.dp, onItemSelection = {
                    selectedValue.value = it
                })
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Cancel",
                        color = Purple40,
                        modifier = Modifier.clickable { navigate(Home) })

                    Button(
                        onClick = {
                            viewModel.onIconChange(icons)
                            navigate(Home)
                        },
                        modifier = Modifier.size(width = 150.dp, height = 40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple40,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Create Category", color = MaterialTheme.colorScheme.onSurface)
                    }
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
                },
            contentAlignment = Alignment.Center
        ) {
            DrawableIcon(
                painter = painterResource(id = item.icon),
                contentDescription = "check",
                tint = if (selectedIndex.value == index) MaterialTheme.colorScheme.background else Color.Transparent
            )
        }
    }
}