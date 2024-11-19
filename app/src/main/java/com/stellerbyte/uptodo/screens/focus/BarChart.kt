package com.stellerbyte.uptodo.screens.focus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stellerbyte.uptodo.ui.theme.ChartColor

@Composable
fun Chart(data: Map<String, Float>, elapsedTimeMillis: Long = 0L) {
    // Define labels for days of the week
    val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    val hoursList = listOf("6h", "5h", "4h", "3h", "2h", "1h", "0h")

    val barGraphHeight = 200.dp
    val barGraphWidth = 24.dp // Increased bar width for better spacing
    val scaleYAxisWidth = 30.dp
    val scaleLineWidth = 2.dp
    val spacing = 8.dp
    // Maximum time scale in milliseconds (6 hours = 6 * 60 * 60 * 1000)
    val maxTimeMillis = 6 * 60 * 60 * 1000L
    // Calculate progress based on elapsed time
    val elapsedProgress = (elapsedTimeMillis / maxTimeMillis.toFloat()).coerceIn(0f, 1f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Y-Axis labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleYAxisWidth),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    hoursList.forEach {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Y-Axis line
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleLineWidth)
                    .background(MaterialTheme.colorScheme.onSurface)
            )

            // Bars for each day
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalAlignment = Alignment.Bottom
            ) {
                daysOfWeek.forEach { day ->
                    // Calculate the dynamic progress for each day
                    val dayProgress = data[day] ?: 0f
                    // Calculate the bar height based on the timer progress
                    val barHeight = barGraphHeight * dayProgress.coerceIn(0f, elapsedProgress)
                    Box(
                        modifier = Modifier
                            .width(barGraphWidth)
                            .height(barHeight)
                            .clip(RoundedCornerShape(2.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }

        // X-Axis line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(scaleLineWidth)
                .background(MaterialTheme.colorScheme.onSurface)
        )

        // X-Axis labels
        Row(
            modifier = Modifier
                .padding(start = scaleYAxisWidth + scaleLineWidth + spacing / 1)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEach {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(barGraphWidth),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}



@Preview
@Composable
fun prviewss() {
    Chart(
        data = mapOf(
            Pair("SUN",0.4f ),
            Pair("MON",0.3f),
            Pair("TUE",0.5f),
            Pair("WED",0.7f),
            Pair( "THU",0.2f),
            Pair( "FRI",0.9f),
            Pair( "SAT",0.8f),

            ),
        elapsedTimeMillis = 1800000L
    )
}
