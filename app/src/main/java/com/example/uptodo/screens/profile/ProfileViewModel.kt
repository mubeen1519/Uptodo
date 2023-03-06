package com.example.uptodo.screens.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.services.implementation.UserProfileData
import com.example.uptodo.services.module.LogService
import com.example.uptodo.services.module.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: ProfileScreenUseCases,
    logService: LogService
) : MainViewModel(logService) {

    var isLoading = mutableStateOf(false)
        private set
    var toastMessage = mutableStateOf("")
        private set
    var userDataStateFromFirebase = mutableStateOf(UserProfileData())
        private set

    init {
        loadImageFromFirebase()
    }
    fun uploadImageToFirebase(url: Uri) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            useCases.uploadPictureToFirebase(url).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        isLoading.value = true
                    }
                    is Response.Success -> {
                        isLoading.value = false
                        updateImageToFirebase(
                            UserProfileData(imageUrl = response.data)
                        )
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

     fun updateImageToFirebase(user: UserProfileData) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            useCases.createOrUpdateProfileToFirebase(user).collect { responce ->
                when (responce) {
                    is Response.Loading -> {
                        isLoading.value = true
                    }
                    is Response.Success -> {
                        isLoading.value = false
                        if (responce.data) {
                            toastMessage.value = "Profile Update"
                        } else {
                            toastMessage.value = "Profile saved"
                        }
                        loadImageFromFirebase()
                    }
                    is Response.Error -> {
                        toastMessage.value = "Something went wrong"
                    }
                }
            }
        }
    }


    private fun loadImageFromFirebase() {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            useCases.loadProfileFromFirebase().collect{responce ->
                when(responce){
                    is Response.Loading ->{
                        isLoading.value = false
                    }
                    is Response.Success -> {
                        userDataStateFromFirebase.value = responce.data
                        delay(1000)
                        isLoading.value = false
                    }
                    is Response.Error ->{}
                }
            }
        }
    }
}