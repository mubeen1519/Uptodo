package com.example.uptodo.services.implementation

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.example.uptodo.services.module.AccountService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {

    override val displayName = Firebase.auth.currentUser?.displayName.toString()
    override val photoUrl = Firebase.auth.currentUser?.photoUrl.toString()
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

    override suspend fun uploadPictureToFirebase(url: Uri) {
        try {
            val userImageId = Firebase.auth.currentUser?.uid
            val imageName = "images/$userImageId.jpg"
            val storageRef = FirebaseStorage.getInstance().reference.child(imageName)

            storageRef.putFile(url).apply {}.await()
            var downloadUrl = ""
            storageRef.downloadUrl.addOnSuccessListener {
                downloadUrl = it.toString()
            }.await()
        } catch (e : Exception){
            print(e.message)
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