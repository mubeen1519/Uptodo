package com.example.uptodo.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.BottomBarColor

sealed class BottomBar(var title: String, var icon: Int, var route: String) {
    object Home : BottomBar("Home", R.drawable.home_icon, com.example.uptodo.navigation.Home)
    object Calender : BottomBar("Calender", R.drawable.calendar, CalenderPage)
    object Focus : BottomBar("Focus", R.drawable.clock, FocusPage)
    object Profile : BottomBar("Profile", R.drawable.user, ProfilePage)
}


@Composable
fun BottomSheet2(
    viewModel: HomeViewModel = hiltViewModel(),
    closeSheet : () -> Unit
) {
    val todo by viewModel.todo
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        )
        {
            Row {
                Box(
                    modifier = Modifier
                        .size(width = 30.dp, height = 30.dp)
                        .background(Color.Black)
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "close",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                                closeSheet()
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = todo.title, color = Color.White, fontSize = 18.sp)
                        DrawableIcon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "edit",
                            tint = Color.White,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Row {
                        Text(
                            text = todo.description,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "time",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "Task Time:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    Text(
                        text = viewModel.getDateAndTime(todo),
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.tag),
                    contentDescription = "category",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "Task Category:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    DrawableIcon(
                        painter = painterResource(id = todo.icon?.icon!!),
                        contentDescription = "icon"
                    )
                    Text(
                        text = todo.icon?.title!!,
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.priority),
                    contentDescription = "priority",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "Task Priority:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    Text(
                        text = todo.priority?.value.toString(),
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.subtask),
                    contentDescription = "subtask",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "Sub-Task:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    Text(
                        text = "Add Sub-Task",
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "delete",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "Delete Task", color = Color.Red)

            }

        }
    }
}