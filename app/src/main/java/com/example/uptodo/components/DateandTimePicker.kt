package com.example.uptodo.components

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateAndTimePicker(
    state: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel(),
    onDateChange : (Long) -> Unit
) {


    val datePickerState = rememberDatePickerState()

    val context = LocalContext.current
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val hour = calendar[Calendar.HOUR_OF_DAY]
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
            modifier = Modifier.size(width = 130.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(id = R.string.chooseTime))
        }
    },
//        dismissButton = {
//            Text(text = "Cancel", color = Purple40, modifier = Modifier.clickable {
//                state.value = false
//            })
//        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.secondary,

            )


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


