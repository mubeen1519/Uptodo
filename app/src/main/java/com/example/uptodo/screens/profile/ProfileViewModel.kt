package com.example.uptodo.screens.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
):MainViewModel(logService) {

    fun uploadImageToFirebase(url : Uri){
        viewModelScope.launch(super.showErrorExceptionHandler){
            accountService.uploadPictureToFirebase(url)
        }
    }
}