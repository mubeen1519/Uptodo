package com.example.uptodo.screens.focus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.patterns.Utility
import com.example.uptodo.components.patterns.Utility.formatTime
import com.example.uptodo.ui.theme.BottomBarColor

@Composable
fun FocusScreen(viewModel: TimerViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val time by viewModel.timeState.observeAsState(Utility.TIME_COUNTDOWN.formatTime())
    val progress by viewModel.progress.observeAsState(1.00f)
    val isStarted by viewModel.isStarted.observeAsState(false)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = com.example.uptodo.R.string.focusTitle),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        TimeSection(time = time, progress = progress, isStarted = isStarted) {
            viewModel.handleCountDownTimer()
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
                    color = Color.White,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                androidx.compose.material3.Button(
                    onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = BottomBarColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(width = 100.dp, height = 30.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "This week", color = Color.White, fontSize = 10.sp)
                }
            }
            Chart(
                data = mapOf(
                    Pair(0.1f, "SUN"),
                    Pair(0.3f, "MON"),
                    Pair(0.5f, "TUE"),
                    Pair(0.7f, "WED"),
                    Pair(0.2f, "THU"),
                    Pair(0.9f, "FRI"),
                    Pair(0.8f, "SAT"),
                ),
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        ) {
            Text(
                text = stringResource(id = R.string.notify),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        CountDownButton(isStarted = isStarted, modifier = Modifier.padding(top = 20.dp)) {
            optionSelected()
        }
    }
}