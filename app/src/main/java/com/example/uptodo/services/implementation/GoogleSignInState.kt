package com.example.uptodo.services.implementation

import com.google.firebase.auth.AuthResult

data class GoogleSignInState(
    val success : AuthResult? = null,
    val loading : Boolean = false,
    val error : String = ""
)
