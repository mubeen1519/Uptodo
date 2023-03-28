package com.example.uptodo.authentication.register

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.navigation.Create_Account
import com.example.uptodo.navigation.Graph
import com.example.uptodo.navigation.Login
import com.example.uptodo.services.Constant
import com.example.uptodo.ui.theme.Purple40
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun RegisterPage(
    navigate: (String) -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    var uiState by viewModel.uiState
    val context = LocalContext.current
    val googleSignInState = viewModel.googleState.value
    LaunchedEffect(key1 = googleSignInState.success) {
        if (googleSignInState.success != null) {
            Toast.makeText(context, "Sign in Success", Toast.LENGTH_SHORT).show()
            navigate(Graph.Home)
        }
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credential)
            } catch (it: ApiException) {
                print(it)
            }
        }
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
                modifier = Modifier.clickable { navigate(Create_Account) }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.register),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 30.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            InputField(
                value = uiState.email,
                placeholderText = stringResource(id = R.string.emailPlaceholder),
                onFieldChange = viewModel::onEmailChange,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelEmail)
            )

            Spacer(modifier = Modifier.height(15.dp))
            InputField(
                value = uiState.password,
                placeholderText = stringResource(id = R.string.passPlaceholder),
                onFieldChange = viewModel::onPasswordChange,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelPassword),
                isFieldSecured = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            InputField(
                value = uiState.confirmPassword,
                placeholderText = stringResource(id = R.string.confirmPass),
                onFieldChange = viewModel::onConfirmPassword,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelConfirmPass),
                isFieldSecured = true
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { viewModel.onRegisterClick(navigate) },
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
                Text(text = stringResource(id = R.string.register))
            }
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Text(
                    text = "or",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    val google = GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestProfile()
                        .requestIdToken(Constant.CLIENT_ID)
                        .build()
                    val googleSign = GoogleSignIn.getClient(context, google)
                    launcher.launch(googleSign.signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(start = 30.dp, end = 30.dp)
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
                DrawableIcon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "google",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = Color.Unspecified
                )
                Text(text = stringResource(id = R.string.registerGoogle))
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(start = 30.dp, end = 30.dp)
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
                DrawableIcon(
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "apple",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(32.dp),
                    tint = Color.Unspecified
                )
                Text(text = stringResource(id = R.string.registerApple))
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp)
                        .align(Alignment.BottomCenter)
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
                                append("" + "Login")
                            }
                        },
                        modifier = Modifier
                            .clickable { navigate(Login) }
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

