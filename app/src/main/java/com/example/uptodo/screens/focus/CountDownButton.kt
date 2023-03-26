package com.example.uptodo.screens.focus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.uptodo.ui.theme.Purple40

@Composable
fun CountDownButton(
    modifier: Modifier = Modifier,
    isStarted: Boolean,
    optionSelected: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { optionSelected() }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Purple40
            ),
            modifier = modifier.size(width = 140.dp, height = 60.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
        val pair = if(!isStarted){
            "Start Focusing"
        }else {
            "Stop Focusing"
        }
            Text(text = pair, color = Color.White)
        }
    }
}