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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.components.categories.LibraryIcon
import com.example.uptodo.navigation.Home
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40

@Composable
fun CategoryPage(navigate: (String) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {

    val iconLibraryState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val selectedValue = remember {
        mutableStateOf(0)
    }

    val values = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Icons.values().forEachIndexed { index, icons ->
            Text(
                text = stringResource(id = R.string.new_category),
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            InputField(
                value = icons.title.replace(icons.title, values.value, ignoreCase = true),
                placeholderText = "Category name",
                onFieldChange = {
                    values.value = it
                },
                label = "Category name:"
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.category_icon), color = Color.LightGray)

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

                val text = stringResource(id = R.string.choose_icon)
                if (selectedValue.value == index) {
                    Text(text = text, color = Color.White)
                } else {
                    IconButton(onClick = {
                        iconLibraryState.value = false
                        selectedValue.value = index
                    }) {
                        DrawableIcon(
                            painter =
                            painterResource(
                                id = when (icons) {
                                    Icons.Grocery -> Icons.Grocery.icon
                                    Icons.Design -> Icons.Design.icon
                                    Icons.Health -> Icons.Health.icon
                                    Icons.Home -> Icons.Home.icon
                                    Icons.Movie -> Icons.Movie.icon
                                    Icons.Music -> Icons.Music.icon
                                    Icons.Social -> Icons.Social.icon
                                    Icons.Sport -> Icons.Sport.icon
                                    Icons.University -> Icons.University.icon
                                    Icons.Work -> Icons.Work.icon
                                }
                            ), contentDescription = "icons",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.category_color), color = Color.LightGray)

            Spacer(modifier = Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EnumColors(itemWidth = 30.dp, onItemSelection = {
                    icons.color
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
                        },
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
                tint = if (selectedIndex.value == index) Color.White else Color.Transparent
            )
        }
    }
}