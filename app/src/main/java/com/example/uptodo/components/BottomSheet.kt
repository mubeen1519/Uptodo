package com.example.uptodo.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.uptodo.R
import com.example.uptodo.components.categories.CategoryDialog
import com.example.uptodo.components.categories.DeleteTaskDialog
import com.example.uptodo.components.categories.PriorityDialog
import com.example.uptodo.screens.category.BottomSheetType
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.BottomBarColor
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetLayout(
    bottomSheetType: BottomSheetType,
    navController: NavHostController,
    state: ModalBottomSheetState,
) {
    when (bottomSheetType) {
        BottomSheetType.TYPE1 -> BottomScreen1(navController = navController, sheetValue = state)
        BottomSheetType.TYPE2 -> BottomScreen2(state = state)
    }

}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomScreen1(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    sheetValue: ModalBottomSheetState,
) {
    val coroutineScope = rememberCoroutineScope()
    val todo by viewModel.todo
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sheetTitle),
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )

            InputField(
                value = todo.title,
                placeholderText = stringResource(id = R.string.titlePlaceholder),
                onFieldChange = viewModel::onTitleChange,
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                value = todo.description,
                placeholderText = stringResource(id = R.string.descriptionPlaceholder),
                onFieldChange = viewModel::onDescriptionChange,
            )
            if (dateState.value) {
                DateAndTimePicker(state = dateState, viewModel, viewModel::onDateChange)
            }
            Row {
                IconButton(onClick = {
                    dateState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.timer),
                        contentDescription = "Timer",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

                if (categoryState.value) {
                    CategoryDialog(
                        navController = navController,
                        state = categoryState
                    )
                }
                IconButton(onClick = {
                    categoryState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.tag),
                        contentDescription = "Tag",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

                if (priorityState.value) {
                    PriorityDialog(
                        state = priorityState
                    )
                }
                IconButton(onClick = {
                    priorityState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.priority),
                        contentDescription = "priority",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    coroutineScope.launch {
                        viewModel.onSaveClick(context)
                        sheetValue.hide()
                    }
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.save),
                        contentDescription = "save",
                        modifier = Modifier
                            .size(25.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomScreen2(
    viewModel: HomeViewModel = hiltViewModel(),
    state: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()
    val todoItem by viewModel.todo


    val deleteDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
                        contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "close",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            scope.launch {
                                state.hide()
                            }
                        }
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
                    checked = false, onCheckedChange = {}, colors = CheckboxDefaults.colors(
                        uncheckedColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary
                    )
                )
                Column {
                    Row {
                        Text(
                            text = todoItem.title,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        DrawableIcon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "edit",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        Text(
                            text = todoItem.description,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "time",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )
                Text(
                    text = stringResource(id = R.string.taskTime),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp)

                ) {
                    Text(
                        text = viewModel.getDateAndTime(todoItem),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)

            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.tag),
                    contentDescription = "category",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )
                Text(
                    text = stringResource(id = R.string.taskCategory),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    todoItem.icon?.let { painterResource(id = it.icon) }?.let {
                        DrawableIcon(
                            painter = it,
                            contentDescription = "icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                    }

                    todoItem.icon?.let {
                        Text(
                            text = it.title,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)

            ) {

                DrawableIcon(
                    painter = painterResource(id = R.drawable.priority),
                    contentDescription = "priority",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(25.dp)
                )

                Text(
                    text = stringResource(id = R.string.taskPriority),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    if (todoItem.priority?.value.toString() == "") {
                        Text(
                            text = "Default",
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                        )
                    } else {
                        Text(
                            text = todoItem.priority?.value.toString(),
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.subtask),
                    contentDescription = "subtask",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.subtask),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.addSubTask),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (deleteDialog.value) {
                DeleteTaskDialog(state = deleteDialog)
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
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
