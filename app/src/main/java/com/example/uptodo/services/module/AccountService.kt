package com.example.uptodo.services.module

import com.example.uptodo.services.implementation.UserProfileData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AccountService {
    fun hasUser(): FirebaseUser?

    fun isAnonymousUser(): Boolean

    fun getUserId(): String

    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun createAccount(
        email: String,
        password: String,
        confirmPassword : String,
        onResult: (Throwable?) -> Unit
    )

    fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun RegisterAccount(email: String,password: String,onResult: (Throwable?) -> Unit)

    fun deleteAccount(onResult: (Throwable?) -> Unit)

    fun signOut()

    suspend fun getUserData(): UserProfileData?

    fun signInWithGoogle(credential: AuthCredential,onResult: (AuthResult?) -> Unit)
}