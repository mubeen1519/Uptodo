package com.stellerbyte.uptodo.screens.splash

import androidx.lifecycle.viewModelScope
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.navigation.Create_Account
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.navigation.Intro_Pages
import com.stellerbyte.uptodo.services.SharedPreferencesUtil
import com.stellerbyte.uptodo.services.module.AccountService
import com.stellerbyte.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : MainViewModel(logService) {
    fun onAppStart(navigate: (String) -> Unit) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            if (SharedPreferencesUtil.isFirstTimeLaunch()) {
                SharedPreferencesUtil.setFirstTimeLaunch(false) // Update for subsequent launches
                navigate(Intro_Pages) // Navigate to Intro Pages
            } else if (accountService.hasUser) {
                accountService.getUserId() // Fetch user data if needed
                navigate(Graph.Home) // Navigate to Home if authenticated
            } else {
                navigate(Create_Account) // Navigate to Create Account if not authenticated
            }
        }
    }
}