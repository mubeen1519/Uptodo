package com.stellerbyte.uptodo.screens.focus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.components.patterns.Utility
import com.stellerbyte.uptodo.components.patterns.Utility.formatTime

@Composable
fun FocusScreen(viewModel: TimerViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val time by viewModel.timeState.observeAsState(Utility.TIME_COUNTDOWN.formatTime())
    val progress by viewModel.progress.observeAsState(1.00f)
    val isStarted by viewModel.isStarted.observeAsState(false)
    val savedProgress = remember { mutableStateOf(viewModel.savedProgress) }
    val elapsedTimeMillis by viewModel.elapsedTimeMillis.observeAsState(0L)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp)
    ) {
        val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
        val (hours, setHours) = remember { mutableStateOf("") }
        val (minutes, setMinutes) = remember { mutableStateOf("") }

        if (showDialog) {
            AlertDialog(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurface,
                onDismissRequest = { setShowDialog(false) },
                title = { Text("Set Timer Duration", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = hours,
                            onValueChange = setHours,
                            label = { Text("Hours", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelSmall) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = minutes,
                            onValueChange = setMinutes,
                            label = { Text("Minutes", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelSmall) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val totalMillis = Utility.getMillis(hours.toIntOrNull() ?: 0, minutes.toIntOrNull() ?: 0)
                        viewModel.startTime(totalMillis)
                        setShowDialog(false)
                    }) {
                        Text("Start Timer", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelSmall)
                    }
                },
                dismissButton = {
                    Button(onClick = { setShowDialog(false) }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelSmall)
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = com.stellerbyte.uptodo.R.string.focusTitle),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        TimeSection(time = time, progress = progress, isStarted = isStarted) {
            if (!isStarted) setShowDialog(true) // Show time picker when timer is not started
            else viewModel.handleCountDownTimer(Utility.getMillis(hours.toIntOrNull() ?: 0, minutes.toIntOrNull() ?: 0)) // Otherwise, pause/resume timer
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Overview",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                androidx.compose.material3.Button(
                    onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.size(width = 100.dp, height = 30.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "This Week",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            // Display the chart with updated progress
            Chart(
                data = savedProgress.value,
                elapsedTimeMillis = elapsedTimeMillis
            )
        }
    }
}

@Composable
fun TimeSection(time: String, progress: Float, isStarted: Boolean, optionSelected: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountDownIndicator(
            progress = progress,
            time = time,
            size = 200,
            stroke = 15,
            modifier = Modifier.padding(20.dp)
        )
        CountDownButton(isStarted = isStarted, modifier = Modifier.padding(top = 20.dp)) {
            optionSelected()
        }
    }
}

