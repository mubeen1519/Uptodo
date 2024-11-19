package com.stellerbyte.uptodo.screens.edittodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.ui.theme.Purple40

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTodoScreen(
    viewModel: EditTodoViewModel = hiltViewModel(),
    todoId: String,
    popUp: () -> Unit
) {
    val todoItem by viewModel.todo
    val deleteDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val categoryState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val priorityState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val dateState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val titleState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.getTodo(todoId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Button(
                    onClick = { popUp() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)
            ) {
                Checkbox(
                    checked = todoItem.completed, onCheckedChange = {viewModel.onTodoCheck(todoItem)}, colors = CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colorScheme.secondary
                    )
                )
                Column {
                    Row {
                        Text(
                            text = todoItem.title,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (titleState.value) {
                            EditTaskTitleDialog(
                                state = titleState,
                                todoItem = todoItem,
                            )
                        }
                        DrawableIcon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "edit",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    titleState.value = true
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        Text(
                            text = todoItem.description,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (dateState.value) {
                EditDateAndTimePicker(state = dateState, onDateChange = viewModel::onDateChange)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "time",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )
                Text(
                    text = stringResource(id = R.string.taskTime),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { dateState.value = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp)

                ) {
                    Text(
                        text = viewModel.getDateAndTime(todoItem),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (categoryState.value) {
                EditCategoryDialog(state = categoryState)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)

            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.tag),
                    contentDescription = "category",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )
                Text(
                    text = stringResource(id = R.string.taskCategory),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { categoryState.value = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    if (todoItem.icon == null) {
                        DrawableIcon(
                            painter = painterResource(id = R.drawable.university),
                            contentDescription = "icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "School",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleSmall
                        )
                    } else {
                        todoItem.icon?.let { painterResource(id = it.icon) }?.let { p1 ->
                            DrawableIcon(
                                painter = p1,
                                contentDescription = "icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                        }
                        todoItem.icon?.let {
                            Text(
                                text = it.title,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (priorityState.value) {
                EditPriorityDialog(state = priorityState)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)

            ) {

                DrawableIcon(
                    painter = painterResource(id = R.drawable.priority),
                    contentDescription = "priority",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )

                Text(
                    text = stringResource(id = R.string.taskPriority),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { priorityState.value = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    if (todoItem.priority?.value.toString() == "") {
                        Text(
                            text = "Default",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleSmall
                        )
                    } else {
                        Text(
                            text = todoItem.priority?.value.toString(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            if (deleteDialog.value) {
                DeleteTaskDialog(state = deleteDialog, popUp = popUp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        deleteDialog.value = true
                    }
            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )
                Text(
                    text = stringResource(id = R.string.deleteTask),
                    color = Color.Red,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            androidx.compose.material3.Button(
                onClick = {
                    viewModel.onSaveClick(context)
                    popUp()
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Edit Task")
            }
        }
    }
}