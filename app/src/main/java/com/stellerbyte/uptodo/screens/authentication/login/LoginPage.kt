package com.stellerbyte.uptodo.screens.authentication.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.components.InputField
import com.stellerbyte.uptodo.navigation.Create_Account
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.navigation.Registration
import com.stellerbyte.uptodo.services.Constant.CLIENT_ID
import com.stellerbyte.uptodo.ui.theme.Purple40
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginPage(
    navigate: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var uiState by viewModel.uiState
    val context = LocalContext.current
//    val googleSignInState = viewModel.googleState.value
//
//    LaunchedEffect(key1 = googleSignInState.success) {
//        if (googleSignInState.success != null) {
//            Toast.makeText(context, "Sign in Success", Toast.LENGTH_SHORT).show()
//            navigate(Graph.Home)
//        }
//    }
//    val launcher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
//            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//            try {
//                val result = account.getResult(ApiException::class.java)
//                val credential = GoogleAuthProvider.getCredential(result.idToken, null)
//                viewModel.googleSignIn(credential)
//            } catch (it: ApiException) {
//                print(it)
//            }
//        }

    BoxWithConstraints(
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
                modifier = Modifier.clickable { navigate(Create_Account) }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 30.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                placeholderText = stringResource(id = R.string.emailPlaceholder),
                onFieldChange = viewModel::onEmailChange,
                value = uiState.email,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelEmail)
            )

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                value = uiState.password,
                placeholderText = stringResource(id = R.string.passPlaceholder),
                onFieldChange = viewModel::onPasswordChange,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelPassword),
                isFieldSecured = true
            )

            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { viewModel.onLoginClick(navigate, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = stringResource(id = R.string.login))
            }
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Row(
//                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Divider(
//                    modifier = Modifier.weight(1f),
//                    color = MaterialTheme.colorScheme.surfaceVariant
//                )
//                Text(
//                    text = "or",
//                    fontSize = 20.sp,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
//                )
//                Divider(
//                    modifier = Modifier.weight(1f),
//                    color = MaterialTheme.colorScheme.surfaceVariant
//                )
//            }

//            Spacer(modifier = Modifier.height(20.dp))
//            Button(
//                onClick = {
//                    val google = GoogleSignInOptions
//                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestEmail()
//                        .requestIdToken(CLIENT_ID)
//                        .build()
//                    val googleSign = GoogleSignIn.getClient(context, google)
//                    launcher.launch(googleSign.signInIntent)
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(46.dp)
//                    .padding(start = 30.dp, end = 30.dp)
//                    .border(
//                        border = BorderStroke(
//                            3.dp,
//                            Purple40
//                        ), shape = RoundedCornerShape(5.dp)
//                    ),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.background,
//                    contentColor = MaterialTheme.colorScheme.onSurface
//                )
//            ) {
//                DrawableIcon(
//                    painter = painterResource(id = R.drawable.google),
//                    contentDescription = "Google",
//                    modifier = Modifier.padding(end = 10.dp),
//                    tint = Color.Unspecified
//                )
//                Text(text = stringResource(id = R.string.googleBtn))
//            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(bottom = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        append("Don't have any account?" + "")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            append("" + "Register")
                        }
                    },
                    modifier = Modifier
                        .clickable { navigate(Registration) }
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

