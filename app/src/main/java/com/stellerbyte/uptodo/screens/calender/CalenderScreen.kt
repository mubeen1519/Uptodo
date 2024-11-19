package com.stellerbyte.uptodo.screens.calender

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.components.SwipeToReveal
import com.stellerbyte.uptodo.screens.home.HomeViewModel
import com.stellerbyte.uptodo.screens.home.TodoCardItems
import com.stellerbyte.uptodo.ui.theme.Purple40
import de.charlex.compose.RevealDirection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalenderScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    openSheet: (String) -> Unit
) {

    var clicked by remember {
        mutableStateOf("")
    }
    val state = remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"))
    val todayDate = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).format(calendar.time)
    val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.calenderTitle),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(45.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            CalendarHorizontal(
                modifier = Modifier.padding(16.dp),
                onSelectedDataChange = {},

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
                    .background(MaterialTheme.colorScheme.secondary)
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
                                .size(width = 140.dp, height = 55.dp)
                                .border(
                                    BorderStroke(
                                        1.dp, Color.White
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (clicked == "today") Purple40 else MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.todayBtn),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                clicked = "completed"
                            },
                            modifier = Modifier
                                .size(width = 140.dp, height = 55.dp)
                                .border(
                                    BorderStroke(
                                        1.dp, Color.White
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (clicked == "completed") Purple40 else MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(5.dp)

                        ) {
                            Text(
                                text = stringResource(id = R.string.completedBtn),
                                style = MaterialTheme.typography.titleMedium
                            )
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
                    items(tasks.value.filter {
                        it.date.contains(
                            todayDate,
                            ignoreCase = true
                        )
                    }, key = { it.id }) { todoItem ->
                        SwipeToReveal(
                            onBackgroundEndClick = {
                                viewModel.onTodoClick(
                                    openSheet,
                                    todoItem
                                )
                            },
                            direction = setOf(
                                RevealDirection.EndToStart
                            ),
                            hiddenContentEnd = {
                                DrawableIcon(
                                    painter = painterResource(id = R.drawable.baseline_edit_24),
                                    contentDescription = "Eye",
                                    tint = Purple40
                                )
                            },
                            onContentClick = { state.value = !state.value },
                            backgroundEndColor = MaterialTheme.colorScheme.background
                        ) {
                            TodoCardItems(
                                todoItem = todoItem,
                                onCheckChange = {
                                    viewModel.onTodoCheck(todoItem)
                                },
                            )
                        }
                    }
                }
            } else if (clicked == "completed") {
                LazyColumn(userScrollEnabled = true) {
                    items(
                        tasks.value.filter { it.completed },
                        key = { it.id }) { todoItem ->
                        SwipeToReveal(
                            onBackgroundEndClick = {
                                viewModel.onTodoClick(
                                    openSheet,
                                    todoItem
                                )
                            },
                            direction = setOf(
                                RevealDirection.EndToStart
                            ),
                            hiddenContentEnd = {
                                DrawableIcon(
                                    painter = painterResource(id = R.drawable.baseline_edit_24),
                                    contentDescription = "Eye",
                                    tint = Purple40
                                )
                            },
                            onContentClick = { state.value = !state.value },
                            backgroundEndColor = MaterialTheme.colorScheme.background
                        ) {
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
}
