package com.example.uptodo.mainViewModel

import androidx.lifecycle.ViewModel
import com.example.uptodo.components.snackbar.SnackBarManager
import com.example.uptodo.components.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.services.implementation.UserProfileData
import com.example.uptodo.services.module.LogService
import kotlinx.coroutines.CoroutineExceptionHandler

open class MainViewModel(private val logService: LogService) : ViewModel() {


    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open fun onError(error: Throwable) {
        SnackBarManager.showMessage(error.toSnackbarMessage())
        logService.logNonFatalCrash(error)
    }

    companion object {
        var userProfileData: UserProfileData? = null
        var todoItem: TODOItem? = null


    }
}