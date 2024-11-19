package com.stellerbyte.uptodo.screens.authentication

import android.app.Activity
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.navigation.BottomBar
import com.stellerbyte.uptodo.navigation.Create_Account
import com.stellerbyte.uptodo.navigation.Intro_Pages
import com.stellerbyte.uptodo.navigation.Login
import com.stellerbyte.uptodo.navigation.Registration
import com.stellerbyte.uptodo.ui.theme.Purple40
import java.util.*

@Composable
fun CreateAccount(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    {

        val context = LocalContext.current
        // Handle the back press action on the Home screen
        BackHandler {
            if (navController.currentBackStackEntry?.destination?.route == Create_Account) {
                // Exit the app if on the Home screen
                (context as? Activity)?.finish()
            } else {
                navController.popBackStack()
            }
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
                onClick = { navController.navigate(Login) },
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
                onClick = { navController.navigate(Registration) },
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

