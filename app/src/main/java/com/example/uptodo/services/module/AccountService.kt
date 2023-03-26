package com.example.uptodo.services.module

import com.example.uptodo.services.implementation.UserProfileData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUserId: String
    val hasUser : Boolean

    val currentUser: Flow<UserProfileData>

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

    fun registerAccount(email: String,password: String,onResult: (Throwable?) -> Unit)

    fun deleteAccount(onResult: (Throwable?) -> Unit)

    fun signInWithGoogle(credential: AuthCredential,onResult: (AuthResult?) -> Unit)

    fun logout()
}