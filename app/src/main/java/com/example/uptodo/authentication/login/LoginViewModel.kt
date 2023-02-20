package com.example.uptodo.authentication.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.authentication.GoogleSignInState
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.Graph
import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.LogService
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

    fun onEmailChange(newValue: String) {
        this.uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        this.uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLoginClick(openScreen: (String) -> Unit) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            accountService.authenticate(email, password) { error ->
                if (error == null) {
                    linkAccount()
                    openScreen(Graph.Home)
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
            accountService.signInWithGoogle(credential) { result ->
                if (result != null) {
                    _googleState.value = GoogleSignInState(success = result)
                }
            }
        }
    }
}