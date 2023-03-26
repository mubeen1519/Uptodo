package com.example.uptodo.splash

import androidx.lifecycle.viewModelScope
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.Graph
import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : MainViewModel(logService) {
    fun onAppStart(navigate : (String) -> Unit) {
        if(accountService.hasUser){
            viewModelScope.launch(super.showErrorExceptionHandler) {
                accountService.getUserId()
            }
            navigate(Graph.Home)
        }
    }
}