package com.stellerbyte.uptodo.screens.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.services.implementation.UserProfileData
import com.stellerbyte.uptodo.services.module.AccountService
import com.stellerbyte.uptodo.services.module.LogService
import com.stellerbyte.uptodo.services.module.Response
import com.stellerbyte.uptodo.services.module.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val useCases: ProfileScreenUseCases,
    private val storageService: StorageService,
    logService: LogService
) : MainViewModel(logService) {

    var isLoading = mutableStateOf(false)
        private set
    var toastMessage = mutableStateOf("")
        private set
    var userDataStateFromFirebase = mutableStateOf(UserProfileData())
        private set

    var username = mutableStateOf("")
    val tasks = storageService.tasks

    val completedTasksCount: Flow<Int> = tasks.map { taskList ->
        taskList.count { it.completed }
    }


    // Computed property to get the count of non-completed tasks
    val nonCompletedTasksCount: Flow<Int> = tasks.map { taskList ->
        taskList.count { !it.completed }
    }


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
            useCases.loadProfileFromFirebase().collect { responce ->
                when (responce) {
                    is Response.Loading -> {
                        isLoading.value = false
                    }

                    is Response.Success -> {
                        userDataStateFromFirebase.value = responce.data
                        delay(1000)
                        isLoading.value = false
                    }

                    is Response.Error -> {}
                }
            }
        }
    }

    fun logout(navigate : (String) -> Unit) {
        viewModelScope.launch {
            try {
                accountService.logout(onSuccess = {
                    navigate(Graph.Authentication)
                }, onError = {
                    Log.d("TAG", "logout: error")
                }) // Implement logout in your accountService
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


    fun changeUsername(username: String) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            accountService.updateUsername(username) { error ->
                if (error == null) {
                    Log.d("TAG", "changeUsername: changed username $username")
                }
            }
        }
    }
}