package com.example.uptodo.authentication

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.authentication.login.LoginViewModel
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.navigation.Create_Account
import com.example.uptodo.navigation.Graph
import com.example.uptodo.navigation.Home
import com.example.uptodo.navigation.Registration
import com.example.uptodo.services.Constant.CLIENT_ID
import com.example.uptodo.ui.theme.Purple40
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
    val googleSignInState = viewModel.googleState.value

    LaunchedEffect(key1 = googleSignInState.success){
        if(googleSignInState.success != null){
            Toast.makeText(context,"Sign in Success",Toast.LENGTH_SHORT).show()
            navigate(Graph.Home)
        }
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException ::class.java)
                val credential = GoogleAuthProvider.getCredential(result.idToken,null)
                viewModel.googleSignIn(credential)
            } catch (it : ApiException){
                print(it)
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        ) {
            DrawableIcon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "ArrowBack",
                tint = Color.LightGray,
                modifier = Modifier.clickable { navigate(Create_Account) }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Login",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 30.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                placeholderText = "Enter your Email",
                onFieldChange = viewModel::onEmailChange,
                value = uiState.email,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = "Email"
            )

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                value = uiState.password,
                placeholderText = "Enter your Password",
                onFieldChange = viewModel::onPasswordChange,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = "Password",
                isFieldSecured = true
            )

            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { viewModel.onLoginClick(navigate) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                Text(
                    text = "or",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                )
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val google = GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(CLIENT_ID)
                        .build()
                    val googleSign = GoogleSignIn.getClient(context, google)
                    launcher.launch(googleSign.signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 30.dp, end = 30.dp)
                    .border(
                        border = BorderStroke(
                            3.dp,
                            Purple40
                        ), shape = RoundedCornerShape(5.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(text = "Login with Google")
                }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 30.dp, end = 30.dp)
                    .border(
                        border = BorderStroke(
                            3.dp,
                            Purple40
                        ), shape = RoundedCornerShape(5.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "google",
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(text = "Login with Apple")
            }
            Spacer(modifier = Modifier.height(50.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Don't have any account?" + "")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            ) {
                                append("" + "Register")
                            }
                        },
                        modifier = Modifier
                            .clickable { navigate(Registration) }
                            .fillMaxWidth(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

