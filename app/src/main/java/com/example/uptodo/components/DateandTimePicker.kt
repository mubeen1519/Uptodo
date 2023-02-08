package com.example.uptodo.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickers(
    dialogState: MaterialDialogState = rememberMaterialDialogState()
) {



    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                Button(
                    onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Purple40,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(width = 150.dp, height = 45.dp)
                ) {
                    positiveButton(
                        "Edit Time",
                        onClick = {},
                        textStyle = TextStyle(color = Color.White)
                    )
                }
                negativeButton("Cancel", onClick = {}, textStyle = TextStyle(color = Purple40))
            },
            backgroundColor = BottomBarColor,
            shape = RoundedCornerShape(8.dp),
            onCloseRequest = {}
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = BottomBarColor,
                    headerTextColor = Color.White,
                    dateActiveBackgroundColor = Purple40,
                    dateActiveTextColor = Color.White,
                    calendarHeaderTextColor = Color.White,
                    dateInactiveBackgroundColor = BottomBarColor
                )
            )
        }
    }


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(){

    val timeDialogState = rememberMaterialDialogState()

    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Purple40,
                contentColor = Color.White
            ),
                modifier = Modifier.size(width = 150.dp, height = 45.dp)
            ) {
                positiveButton(
                    "Edit",
                    onClick = {},
                    textStyle = TextStyle(color = Color.White)
                )
            }
            negativeButton("Cancel", onClick = {}, textStyle = TextStyle(color = Purple40))
        },
        backgroundColor = BottomBarColor,
        shape = RoundedCornerShape(8.dp),
        onCloseRequest = {}
    ) {
        timepicker(
            initialTime = LocalTime.now(),
            colors = TimePickerDefaults.colors(
                inactiveBackgroundColor = BottomBarColor,
                headerTextColor = Color.White,
                selectorColor = Purple40,
                selectorTextColor = Color.White,
                borderColor = BottomBarColor,
                activeTextColor = Color.White,
            )
        )
    }

}