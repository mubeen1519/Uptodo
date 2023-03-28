package com.example.uptodo.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.navigation.Intro_Pages
import com.example.uptodo.navigation.Login
import com.example.uptodo.navigation.Registration
import com.example.uptodo.ui.theme.Purple40
import java.util.*

@Composable
fun CreateAccount(navigate: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        ) {
            DrawableIcon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "ArrowBack",
                tint = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clickable { navigate(Intro_Pages) }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.welcometxt),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(id = R.string.accountInstruction),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.surfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp, top = 30.dp, start = 30.dp, end = 30.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
        ) {
            Button(
                onClick = { navigate(Login) },
                modifier = Modifier.size(width = 300.dp, height = 40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = stringResource(id = R.string.login).uppercase(Locale.ROOT))
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navigate(Registration) },
                modifier = Modifier
                    .size(width = 300.dp, height = 40.dp)
                    .border(
                        border = BorderStroke(
                            3.dp,
                            Purple40
                        ), shape = RoundedCornerShape(5.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = stringResource(id = R.string.createAccount).uppercase(Locale.ROOT))
            }
        }
    }


}

