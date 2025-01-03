package com.stellerbyte.uptodo.components

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.screens.home.HomeViewModel
import com.stellerbyte.uptodo.ui.theme.Purple40
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateAndTimePicker(
    state: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel(),
    selectedDateState : MutableState<Long?>,
    selectedTimeHour : MutableState<Int?>,
    selectedMinutes : MutableState<Int?>,
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
            selectedTimeHour.value = hours
            selectedMinutes.value = minutes
        }, hour, minute, false
    )


    DatePickerDialog(onDismissRequest = { state.value = false }, confirmButton = {
        Button(
            onClick = {
                timePickerDialog.show()
                state.value = false
                datePickerState.selectedDateMillis?.let {
                    onDateChange(it)
                    selectedDateState.value = it
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(id = R.string.chooseTime))
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderDateAndTimePicker(
    state: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel(),
    onDateChange: (Long) -> Unit
) {
    val todo by viewModel.todo
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
            Text(text = stringResource(id = R.string.chooseTime))
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
            dateValidator = { date ->
                val todoDate = todo.date
                todoDate.let {
                    val todoDateTime = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).parse(it)
                    val selectedDate = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).parse(
                        SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).format(
                            Date(date)
                        )
                    )
                    selectedDate?.equals(todoDateTime) ?: true || selectedDate?.after(todoDateTime) ?:true
                }?: true
            }
        )
    }
}


