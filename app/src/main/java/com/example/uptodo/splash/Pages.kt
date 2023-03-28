package com.example.uptodo.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uptodo.crousel.HorizontalPages
import com.example.uptodo.navigation.Create_Account
import com.example.uptodo.ui.theme.Purple40

@Composable
fun Pages(
    navigate : (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Text(
            text = "SKIP",
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp, start = 10.dp).clickable { navigate(Create_Account) }
        )
        Row(modifier = Modifier.padding(top = 70.dp)) {
            HorizontalPages()
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Back",
                modifier = Modifier
                    .clickable { }
                    .weight(1f)
                    .padding(30.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Button(
                onClick = { navigate(Create_Account) },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 16.dp)
            ) {
                Text(text = "Next")
            }
        }

    }
}

