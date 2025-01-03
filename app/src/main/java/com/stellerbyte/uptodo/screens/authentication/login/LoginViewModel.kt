package com.stellerbyte.uptodo.screens.authentication.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.stellerbyte.uptodo.screens.authentication.GoogleSignInState
import com.stellerbyte.uptodo.components.patterns.isValidEmail
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.services.module.AccountService
import com.stellerbyte.uptodo.services.module.LogService
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService
) : MainViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    private fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun onEmailChange(newValue: String) {
        this.uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        this.uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLoginClick(openScreen: (String) -> Unit,context: Context) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            if(!email.isValidEmail()){
                showToast(context,"Please Insert Valid Email")
            }
            if(email.isEmpty() && password.isEmpty()){
                showToast(context,"Please fill all the fields")
            }
            accountService.authenticate(email, password) { error ->
                if (error == null) {
                    linkAccount()
                    openScreen(Graph.Home)
                    showToast(context,"Authenticated Successfully")
                }
            }
        }
    }

    private fun linkAccount() {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            accountService.linkAccount(email, password) { error ->
                if (error != null) {
                    logService.logNonFatalCrash(error)
                }
            }
        }
    }

    fun googleSignIn(credential: AuthCredential) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
//            accountService.signInWithGoogle(credential) { result ->
//                if (result != null) {
//                    _googleState.value = GoogleSignInState(success = result)
//                }
//            }
        }
    }
}