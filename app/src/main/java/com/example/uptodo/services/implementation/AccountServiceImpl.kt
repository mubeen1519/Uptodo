package com.example.uptodo.services.implementation

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.example.uptodo.services.module.AccountService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {
    override fun hasUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override fun isAnonymousUser(): Boolean {
        return Firebase.auth.currentUser?.isAnonymous ?: true
    }

    override fun getUserId(): String {
        return Firebase.auth.currentUser?.uid.orEmpty()
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
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
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                userProfileData.id = it.result.user?.uid
                onResult(it.exception)
            }
    }

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun deleteAccount(onResult: (Throwable?) -> Unit) {
        Firebase.auth.currentUser!!.delete()
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun getUserData(): UserProfileData? {
        TODO("Not yet implemented")
    }

    override fun RegisterAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signInWithGoogle(credential: AuthCredential, onResult: (AuthResult?) -> Unit) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(it.result)
                }
            }


    }
}