package com.stellerbyte.uptodo.screens.authentication.register

import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CustomCredential
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseUser
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.screens.authentication.GoogleSignInState
import com.stellerbyte.uptodo.screens.profile.ProfileScreenUseCases
import com.stellerbyte.uptodo.services.implementation.UserProfileData
import com.stellerbyte.uptodo.services.module.AccountService
import com.stellerbyte.uptodo.services.module.GoogleAuthService
import com.stellerbyte.uptodo.services.module.LogService
import com.stellerbyte.uptodo.services.module.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService,
    private val profileScreenUseCases: ProfileScreenUseCases,
    private val googleAuthService: GoogleAuthService
) : MainViewModel(logService) {
    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    // Function to set a toast message
    fun setToastMessage(message: String) {
        _toastMessage.value = message
    }


    var uiState = mutableStateOf(RegisterUiState())
    private val email get() = uiState.value.email
    private val password get() = uiState.value.password
    private val confirmPassword get() = uiState.value.confirmPassword
    private val username get() = uiState.value.username
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onConfirmPassword(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    fun onUsernameChanged(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onRegisterClick(navigate: (String) -> Unit) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            accountService.createAccount(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                username = username
            ) { error ->
                if (error == null) {
                    linkRegisterAccount()
                    firstTimeCreateProfile(
                        user = UserProfileData(
                            email = email, username = username,
                        )
                    )
                    navigate(Graph.Home)
                } else {
                    setToastMessage("The email address is already in use by another account.")
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

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun signInWithGoogle(
        navigate: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                googleAuthService.signIn()
                navigate(Graph.Home)
            } catch (e: GetCredentialException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun handleSignIn(
        result: androidx.credentials.GetCredentialResponse,
        onResult: (FirebaseUser?) -> Unit
    ) {
        val credential = result.credential

        when {
            credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val token = googleIdTokenCredential.idToken
                    accountService.firebaseAuthWithGoogle(token, onResult)
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("TAG", "Received an invalid Google ID token response", e)
                    onResult(null)
                }
            }

            else -> onResult(null)
        }
    }

    private fun firstTimeCreateProfile(user: UserProfileData) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            profileScreenUseCases.createOrUpdateProfileToFirebase(user = user)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            setToastMessage("")
                        }

                        is Response.Success -> {
                            if (response.data) {
                                setToastMessage("Profile updated")
                            } else {
                                setToastMessage("Profile created")
                            }
                        }

                        is Response.Error -> {
                            setToastMessage("Update Failed")
                        }
                    }
                }
        }
    }
}

