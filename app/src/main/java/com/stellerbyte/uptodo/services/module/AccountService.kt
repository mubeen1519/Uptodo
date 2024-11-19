package com.stellerbyte.uptodo.services.module

import com.stellerbyte.uptodo.services.implementation.UserProfileData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUserId: String
    val hasUser : Boolean

    val currentUser: Flow<UserProfileData>

    fun isAnonymousUser(): Boolean

    fun getUserId(): String
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun createAccount(
        username : String,
        email: String,
        password: String,
        confirmPassword : String,
        onResult: (Throwable?) -> Unit
    )
    fun saveUsernameToFirestore(userProfileData: UserProfileData)
    fun updateUsername(newUsername: String, onResult: (Throwable?) -> Unit)
    fun fetchUsernameFromFirestore(uid: String, callback: (String?) -> Unit)
    fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun registerAccount(email: String,password: String,onResult: (Throwable?) -> Unit)


    fun firebaseAuthWithGoogle(idTokenString: String, onResult: (FirebaseUser?) -> Unit)

    fun logout(onSuccess: () -> Unit, onError: (Exception) -> Unit)

        fun deleteAccount(email: String,password: String, onResult: (Throwable?) -> Unit)
}