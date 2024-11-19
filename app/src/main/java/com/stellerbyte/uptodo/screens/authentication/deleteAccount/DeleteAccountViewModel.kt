package com.stellerbyte.uptodo.screens.authentication.deleteAccount

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.services.module.AccountService
import com.stellerbyte.uptodo.services.module.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    val accountService: AccountService,
    logService: LogService
) : MainViewModel(logService) {


    var uiState = mutableStateOf(DeleteUiState())
    private val email get() = uiState.value.email
    private val password get() = uiState.value.password
    private fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun onEmailChange(newValue: String) {
        this.uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        this.uiState.value = uiState.value.copy(password = newValue)
    }


    fun onDeleteClick(navigate: (String) -> Unit, context: Context) {
        viewModelScope.launch(super.showErrorExceptionHandler){
            accountService.deleteAccount(email, password){
                showToast(context,"Account Deleted Successfully")
                navigate(Graph.Authentication)
            }
        }
    }
}