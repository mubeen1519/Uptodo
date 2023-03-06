package com.example.uptodo.services.implementation

import android.net.Uri
import com.example.uptodo.services.module.ProfileScreenRepo
import com.example.uptodo.services.module.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileScreenImp @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) : ProfileScreenRepo {
    override suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>> = flow {
        val userImageId = UUID.randomUUID()
        val imageName = "images/$userImageId.jpg"
        try {

            emit(Response.Loading)
            val storageRef = storage.reference.child(imageName)
            storageRef.putFile(url).apply {}.await()
            var downloadUrl = ""
            storageRef.downloadUrl.addOnSuccessListener {
                downloadUrl = it.toString()
            }.await()
            emit(Response.Success(downloadUrl))

        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "error"))
        }
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Response.Loading)
            auth.signOut().apply {
                this@callbackFlow.trySendBlocking(Response.Success(true))
            }
        } catch (e : Exception){
            this@callbackFlow.trySendBlocking(Response.Error(e.message?: "error"))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun createOrUpdateProfileToFirebase(user: UserProfileData): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                val userId = auth.currentUser?.uid.toString()
                val userEmail = auth.currentUser?.email.toString()

                val databaseRef =
                    database.getReference("Profiles").child(userId).child("profile")
                val childUpdates = mutableMapOf<String, Any>()
                childUpdates["/id/"] = userId
                childUpdates["/username/"] = userEmail

                if (user.imageUrl != "") childUpdates["/imageUrl/"] =
                    user.imageUrl
                databaseRef.updateChildren(childUpdates).await()
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: "error adding into database"))
            }
        }

    override suspend fun loadProfileFromFirebase(): Flow<Response<UserProfileData>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Response.Loading)
            val userId = auth.currentUser?.uid
            val databaseRef = database.getReference("Profiles")
            val postListener = databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userFromFirebaseDatabase = snapshot.child(userId!!).child("profile")
                        .getValue(UserProfileData::class.java) ?: UserProfileData()
                    this@callbackFlow.trySendBlocking(Response.Success(userFromFirebaseDatabase))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Response.Error(error.message))
                }

            })
            databaseRef.addValueEventListener(postListener)
            awaitClose {
                databaseRef.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Response.Error(e.message ?: "error"))
        }
    }

}