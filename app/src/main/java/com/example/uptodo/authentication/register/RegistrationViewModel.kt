package com.example.uptodo.authentication.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.authentication.GoogleSignInState
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.Graph
import com.example.uptodo.screens.profile.ProfileScreenUseCases
import com.example.uptodo.services.implementation.UserProfileData
import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.LogService
import com.example.uptodo.services.module.Response
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService,
    private val profileScreenUseCases: ProfileScreenUseCases
) : MainViewModel(logService) {
    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState
    var toastMessage = mutableStateOf("")
        private set


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
                    firstTimeCreateProfile()
                    navigate(Graph.Home)
                }
            }
        }
    }

    private fun linkRegisterAccount() {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            accountService.registerAccount(email, password) { error ->
                if (error != null) logService.logNonFatalCrash(error)
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

    fun firstTimeCreateProfile() {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            profileScreenUseCases.createOrUpdateProfileToFirebase(UserProfileData()).collect {response ->
                when(response){
                    is Response.Loading ->{
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        if(response.data){
                            toastMessage.value = "Profile updated"
                        } else {
                            toastMessage.value = "Profile created"
                        }
                    }
                    is Response.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }
}

