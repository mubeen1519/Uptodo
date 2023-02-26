package com.example.uptodo.screens.focus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.components.patterns.Utility
import com.example.uptodo.components.patterns.Utility.formatTime

@Composable
fun FocusScreen(viewModel: TimerViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val time by viewModel.timeState.observeAsState(Utility.TIME_COUNTDOWN.formatTime())
    val progress by viewModel.progress.observeAsState(1.00f)
    val isStarted by viewModel.isStarted.observeAsState(false)

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(bottom = 50.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Focus Mode", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))
        TimeSection(time = time, progress = progress, isStarted = isStarted) {
            viewModel.handleCountDownTimer()
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Overview", color = Color.White, fontSize = 15.sp)
            Chart(
                data = mapOf(
                    Pair(0.5f, "SUN"),
                    Pair(0.3f, "MON"),
                    Pair(0.5f, "TUE"),
                    Pair(0.7f, "WED"),
                    Pair(0.2f, "THU"),
                    Pair(0.9f, "FRI"),
                    Pair(0.8f, "SAT"),

                    ), hours = listOf(
                    "1h", "2h", "3h", "4h", "5h", "6h"
                )
            )
        }
    }
}
@Composable
fun TimeSection(time: String, progress: Float, isStarted: Boolean, optionSelected: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        CountDownIndicator(
            progress = progress,
            time = time,
            size = 200,
            stroke = 15,
            modifier = Modifier.padding(20.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "While your focus mode is on, all of your notification will be off",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        CountDownButton(isStarted = isStarted, modifier = Modifier.padding(top = 20.dp)) {
            optionSelected()
        }
    }
}