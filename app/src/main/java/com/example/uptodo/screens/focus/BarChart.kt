package com.example.uptodo.screens.focus

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uptodo.ui.theme.ChartColor

@Composable
fun Chart(data: Map<Float, String>) {

    val hoursList = listOf(
        "6h", "5h", "4h", "3h", "2h", "1h"
    )
    //Graph dimension
    val barGraphHeight by remember {
        mutableStateOf(200.dp)
    }
    val barGraphWidth by remember {
        mutableStateOf(20.dp)
    }

    val text by remember {
        mutableStateOf("")
    }

    //scale graph
    val scaleYAxisWidth by remember { mutableStateOf(30.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            //scale y axis
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleYAxisWidth),
                contentAlignment = Alignment.BottomCenter
            ) {

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Top
                ) {
                    hoursList.forEach {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 15.dp)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleLineWidth)
                    .background(MaterialTheme.colorScheme.onSurface)
            )

            // graph
            data.forEach {
                Box(
                    modifier = Modifier
                        .padding(start = barGraphWidth, bottom = 5.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .width(barGraphWidth)
                        .fillMaxHeight(it.key)
                        .background(ChartColor)

                )
            }
        }
        // X-Axis Line
        Box(
            modifier = Modifier
                .fillMaxWidth(0.97f)
                .height(scaleLineWidth)
                .background(MaterialTheme.colorScheme.onSurface)
        )

        // Scale X-Axis
        Row(
            modifier = Modifier
                .padding(start = scaleYAxisWidth + barGraphWidth)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
        ) {

            data.values.forEach {
                Text(
                    modifier = Modifier.width(barGraphWidth),
                    text = it,
                    textAlign = TextAlign.Center,
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
            Pair(0.4f, "SUN"),
            Pair(0.3f, "MON"),
            Pair(0.5f, "TUE"),
            Pair(0.7f, "WED"),
            Pair(0.2f, "THU"),
            Pair(0.9f, "FRI"),
            Pair(0.8f, "SAT"),

            ),
    )
}
