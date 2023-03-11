package com.example.uptodo.services.implementation

import com.example.uptodo.services.module.AccountService
import com.google.firebase.auth.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth : FirebaseAuth) : AccountService {

    override fun hasUser(): FirebaseUser? {
        return auth.currentUser
    }

    override val currentUser: Flow<UserProfileData>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { UserProfileData(it.uid) } ?: UserProfileData())
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override fun isAnonymousUser(): Boolean {
        return auth.currentUser?.isAnonymous ?: true
    }

    override fun getUserId(): String {
        return auth.currentUser?.uid.orEmpty()
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(it.exception)
                }
            }
    }

    override fun createAccount(
        email: String,
        password: String,
        confirmPassword: String,
        onResult: (Throwable?) -> Unit
    ) {
        val userProfileData = UserProfileData().apply {
            this.username = email
            this.password = password
            this.confirmPassword = confirmPassword
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                userProfileData.id = it.result.user?.uid!!
                onResult(it.exception)
            }
    }

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun deleteAccount(onResult: (Throwable?) -> Unit) {
        auth.currentUser!!.delete()
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }


    override suspend fun getUserData(): UserProfileData? {
        TODO("Not yet implemented")
    }

    override fun RegisterAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signInWithGoogle(credential: AuthCredential, onResult: (AuthResult?) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(it.result)
                }
            }


    }
}