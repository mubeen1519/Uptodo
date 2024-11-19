package com.stellerbyte.uptodo.screens.edittodo

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.CommonDialog
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.components.InputField
import com.stellerbyte.uptodo.screens.category.Icons
import com.stellerbyte.uptodo.screens.category.Priority
import com.stellerbyte.uptodo.services.implementation.TODOItem
import com.stellerbyte.uptodo.ui.theme.Purple40
import java.util.Calendar
import java.util.TimeZone

@Composable
fun EditTaskTitleDialog(
    state: MutableState<Boolean>,
    todoItem: TODOItem,
) {
    CommonDialog(state = state) {
        EditTaskTitleContent(todoItem = todoItem,state = state)
    }
}

@Composable
private fun EditTaskTitleContent(
    todoItem: TODOItem,
    viewModel: EditTodoViewModel = hiltViewModel(),
    state: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Edit Task title",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        InputField(
            value = todoItem.title,
            placeholderText = stringResource(id = R.string.titlePlaceholder),
            onFieldChange = viewModel::onTitleChange,
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            value = todoItem.description,
            placeholderText = stringResource(id = R.string.descriptionPlaceholder),
            onFieldChange = viewModel::onDescriptionChange,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = { state.value = false }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Cancel")
            }
            Button(
                onClick = {
                    state.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Edit Task")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditDateAndTimePicker(
    state: MutableState<Boolean>,
    viewModel: EditTodoViewModel = hiltViewModel(),
    onDateChange: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    val context = LocalContext.current
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val hour = calendar[Calendar.HOUR]
    val minute = calendar[Calendar.MINUTE]

    val time = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        R.style.themeOverlay_timePicker,
        { _, hours: Int, minutes: Int ->
            viewModel.onTimeChange(hours, minutes)
            time.value = "$hours:$minutes"
        }, hour, minute, false
    )


    DatePickerDialog(onDismissRequest = { state.value = false }, confirmButton = {
        Button(
            onClick = {
                timePickerDialog.show()
                state.value = false
                datePickerState.selectedDateMillis?.let { onDateChange(it) }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(id = R.string.EditTime))
        }
    },
        dismissButton = {
            Button(
                onClick = { state.value = false },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Purple40
                )
            ) {
                Text(text = "Cancel")
            }

        },
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                selectedDayContainerColor = Purple40,
                headlineContentColor = MaterialTheme.colorScheme.onSurface,
                yearContentColor = MaterialTheme.colorScheme.onSurface,
                selectedYearContainerColor = MaterialTheme.colorScheme.secondary,
                weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                currentYearContentColor = MaterialTheme.colorScheme.onSurface,
                todayContentColor = MaterialTheme.colorScheme.onSurface,
                dayContentColor = MaterialTheme.colorScheme.onSurface,
                subheadContentColor = MaterialTheme.colorScheme.secondary,
                disabledDayContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}
@Composable
fun EditCategoryDialog(
    state: MutableState<Boolean>,
    viewModel: EditTodoViewModel = hiltViewModel(),
) {
    CommonDialog(state = state) {
        EditCategoryContent(
            dialogState = state,
            viewModel = viewModel
        ) {}
    }
}
@Composable
private fun EditCategoryContent(
    viewModel: EditTodoViewModel = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int?) -> Unit,
){
    val todo by viewModel.todo

    val selectedIndex = remember {
        mutableStateOf(todo.icon?.icon)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.chooseCategory),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        )
        {
            itemsIndexed(Icons.values()) { index, icons ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (selectedIndex.value == index) Purple40 else MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(width = 60.dp, height = 60.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(icons.color),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = {
                                selectedIndex.value = index
                                onItemSelection(selectedIndex.value)
                                viewModel.onIconChange(icons)
                            },
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = icons.icon),
                                contentDescription = "icons",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Text(
                        text = icons.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.editCategory),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
@Composable
fun EditPriorityDialog(state: MutableState<Boolean>, viewModel: EditTodoViewModel = hiltViewModel()) {
    CommonDialog(state = state) {
        EditPriorityContent(
            dialogState = state,
            viewModel = viewModel,
            onItemSelection = {}
        )
    }
}

@Composable
private fun EditPriorityContent(
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int?) -> Unit,
    viewModel: EditTodoViewModel = hiltViewModel(),
){
    val todo by viewModel.todo
    val dialogContent: List<Priority>
    dialogContent = ArrayList()
    val selectedIndex = remember {
        mutableStateOf(todo.priority?.value)
    }
    for (i in dialogContent) {
        i.icon
        i.value.toString()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.priorityTitle),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(Priority.values()) { index, priority ->
                Column {
                    Box(
                        modifier = Modifier
                            .size(width = 60.dp, height = 60.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(if (selectedIndex.value == index) Purple40 else MaterialTheme.colorScheme.onSecondary)
                            .padding(5.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        IconButton(
                            modifier = Modifier.size(25.dp),
                            onClick = {
                                selectedIndex.value = index
                                onItemSelection(selectedIndex.value)
                                viewModel.onPriorityChange(priority)

                            },
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = priority.icon),
                                contentDescription = "icons",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        Text(
                            text = priority.value.toString(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 30.dp),

                            )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(width = 150.dp, height = 40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.editCategory),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun DeleteTaskDialog(state: MutableState<Boolean>, popUp: () -> Unit) {
    CommonDialog(state = state) {
        DeleteTaskContent(state = state, popUp = popUp)
    }
}

@Composable
fun DeleteTaskContent(
    state: MutableState<Boolean>,
    viewModel: EditTodoViewModel = hiltViewModel(),
    popUp: () -> Unit
) {
    val todo by viewModel.todo
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(id = R.string.deleteTask),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.deleteDialog),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Task title: ${todo.title}", color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { state.value = false }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Purple40
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.onDelete(todo)
                        state.value = false
                        popUp()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
        }
    }
}