package com.stellerbyte.uptodo.services.module

import androidx.credentials.GetCredentialResponse

interface GoogleAuthService {
    fun isSingedIn() : Boolean
    suspend fun signIn() : Boolean
    suspend fun handleSingIn(result: GetCredentialResponse): Boolean
    suspend fun buildCredentialRequest(): GetCredentialResponse
    suspend fun signOut()
}