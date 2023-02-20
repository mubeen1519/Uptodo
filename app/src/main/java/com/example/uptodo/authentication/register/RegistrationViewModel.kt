package com.example.uptodo.authentication.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.authentication.GoogleSignInState
import com.example.uptodo.components.patterns.isValidEmail
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.Graph
import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.LogService
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService
) : MainViewModel(logService) {
    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState


    var uiState = mutableStateOf(RegisterUiState())
    private val email get() = uiState.value.email
    private val password get() = uiState.value.password
    private val confirmPassword get() = uiState.value.confirmPassword

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onConfirmPassword(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    fun onRegisterClick(navigate: (String) -> Unit) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            accountService.createAccount(email, password, confirmPassword) { error ->
                if (error == null) {
                    linkRegisterAccount()
                    navigate(Graph.Home)
                }
            }
        }
    }

    private fun linkRegisterAccount(){
        viewModelScope.launch(super.showErrorExceptionHandler){
            accountService.RegisterAccount(email,password){ error ->
                if(error != null) logService.logNonFatalCrash(error)
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

