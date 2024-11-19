package com.stellerbyte.uptodo.screens.authentication.register

data class RegisterUiState(
    var username : String = "",
    var email :String = "",
    var password : String = "",
    var confirmPassword : String = ""
)
