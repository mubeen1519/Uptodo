package com.example.uptodo.screens.focus

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uptodo.ui.theme.ChartColor

@Composable
fun Chart(data: Map<Float, String>, hours: List<String>) {
    val context = LocalContext.current

    //Graph dimension
    val barGraphHeight by remember {
        mutableStateOf(200.dp)
    }
    val barGraphWidth by remember {
        mutableStateOf(15.dp)
    }

    //scale graph
    val scaleYAxisWidth by remember { mutableStateOf(2.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }

    Column(modifier = Modifier
        .fillMaxWidth().padding(20.dp)) {
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
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = hours.toString(), color = Color.White)
                    Spacer(modifier = Modifier.fillMaxHeight())
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = hours.toString(), color = Color.White)
                    Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleLineWidth)
                    .background(Color.White)
            )

            // graph
            data.forEach {
                Box(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .width(barGraphWidth)
                        .fillMaxHeight(it.key)
                        .background(ChartColor)

                )
            }
        }
        // X-Axis Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(scaleLineWidth)
                .background(Color.White)
        )

        // Scale X-Axis
        Row(
            modifier = Modifier
                .padding(start = scaleYAxisWidth + barGraphWidth + scaleLineWidth)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
        ) {

            data.values.forEach {
                Text(
                    modifier = Modifier.width(barGraphWidth),
                    text = it,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun prviewss(){
    Chart(data = mapOf(
        Pair(0.5f, "SUN"),
        Pair(0.3f, "MON"),
        Pair(0.5f, "TUE"),
        Pair(0.7f, "WED"),
        Pair(0.2f, "THU"),
        Pair(0.9f, "FRI"),
        Pair(0.8f, "SAT"),

        ), hours = listOf(
        "1h", "2h", "3h", "4h", "5h", "6h"),
        )
}
