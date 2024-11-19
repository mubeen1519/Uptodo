package com.stellerbyte.uptodo.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.screens.home.HomeViewModel
import com.stellerbyte.uptodo.services.implementation.Notifications.ScheduleNotification
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomScreen(
    viewModel: HomeViewModel = hiltViewModel(),
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

    val selectedDateMillis = remember { mutableStateOf<Long?>(null) }
    val selectedHour = remember { mutableStateOf<Int?>(null) }
    val selectedMinute = remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sheetTitle),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
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
                DateAndTimePicker(state = dateState, viewModel = viewModel, onDateChange = viewModel::onDateChange,selectedDateState = selectedDateMillis, selectedMinutes = selectedMinute, selectedTimeHour = selectedHour)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                IconButton(onClick = {
                    dateState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.timer),
                        contentDescription = "Timer",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

                if (categoryState.value) {
                    CategoryDialog(
                        state = categoryState
                    )
                }
                IconButton(onClick = {
                    categoryState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.tag),
                        contentDescription = "Tag",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(30.dp)
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
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    coroutineScope.launch {
                        viewModel.onSaveClick(context)
                        selectedDateMillis.value?.let { dateMillis ->
                            selectedHour.value?.let { hour ->
                                selectedMinute.value?.let { minute ->
                                    ScheduleNotification().scheduleNotification(
                                        context = context,
                                        dateMillis = dateMillis,
                                        hour = hour,
                                        minute = minute,
                                        title = todo.title ?: "Reminder"
                                    )
                                }
                            }
                        }
                        sheetValue.hide()
                    }
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.save),
                        contentDescription = "save",
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

