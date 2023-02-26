package com.example.uptodo.screens.calender

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.screens.home.TodoCardItems
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.component.day.config.KalendarDayColors
import com.himanshoe.kalendar.component.header.config.KalendarHeaderConfig
import com.himanshoe.kalendar.component.text.config.KalendarTextColor
import com.himanshoe.kalendar.component.text.config.KalendarTextConfig
import com.himanshoe.kalendar.component.text.config.KalendarTextSize
import com.himanshoe.kalendar.ui.oceanic.KalendarOceanic
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalenderScreen(viewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(viewModel) {
        viewModel.initailizeTodo()
    }
    var clicked by remember {
        mutableStateOf("")
    }
    val calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"))
    val todayDate = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).format(calendar.time)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(text = "Calender", color = Color.White, fontSize = 20.sp)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(45.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            KalendarOceanic(
                takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                modifier = Modifier
                    .fillMaxWidth(),
                kalendarDayColors = KalendarDayColors(
                    textColor = Color.White,
                    selectedTextColor = Color.White
                ),
                kalendarThemeColor = KalendarThemeColor(
                    backgroundColor = BottomBarColor,
                    dayBackgroundColor = Purple40,
                    headerTextColor = Color.White,
                ),
                kalendarHeaderConfig = KalendarHeaderConfig(
                    kalendarTextConfig = KalendarTextConfig(
                        kalendarTextColor = KalendarTextColor(
                            Color.White
                        ),
                        kalendarTextSize = KalendarTextSize.Title
                    )
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BottomBarColor)
                    .clip(shape = RoundedCornerShape(5.dp))
            ) {
                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Button(
                            onClick = {
                                clicked = "today"

                            },
                            modifier = Modifier
                                .size(width = 140.dp, height = 55.dp).border(
                                    BorderStroke(
                                        1.dp, Color.White
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (clicked == "today") Purple40 else BottomBarColor,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(text = "Today")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                clicked = "completed"
                            },
                            modifier = Modifier
                                .size(width = 140.dp, height = 55.dp).border(
                                    BorderStroke(
                                        1.dp, Color.White
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (clicked == "completed") Purple40 else BottomBarColor,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(5.dp)

                        ) {
                            Text(text = "Completed")
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 80.dp)
        ) {
            if (clicked == "today") {
                LazyColumn(userScrollEnabled = true) {
                    items(viewModel.allUserTodo.filter {
                        it.date.contains(
                            todayDate,
                            ignoreCase = true
                        )
                    }, key = { it.id }) { todoItem ->
                        TodoCardItems(
                            todoItem = todoItem,
                            onCheckChange = {
                                viewModel.onTodoCheck(todoItem)
                            },
                        )
                    }
                }
            } else if (clicked == "completed") {
                LazyColumn(userScrollEnabled = true) {
                    items(
                        viewModel.allUserTodo.filter { it.completed },
                        key = { it.id }) { todoItem ->
                        TodoCardItems(
                            todoItem = todoItem,
                            onCheckChange = {
                                viewModel.onTodoCheck(todoItem)
                            },
                        )
                    }
                }
            }
        }

    }
}
