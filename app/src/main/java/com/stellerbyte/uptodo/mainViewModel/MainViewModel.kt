package com.stellerbyte.uptodo.mainViewModel

import androidx.lifecycle.ViewModel
import com.stellerbyte.uptodo.services.module.LogService
import kotlinx.coroutines.CoroutineExceptionHandler

open class MainViewModel(private val logService: LogService) : ViewModel() {


    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open fun onError(error: Throwable) {
        logService.logNonFatalCrash(error)
    }


}