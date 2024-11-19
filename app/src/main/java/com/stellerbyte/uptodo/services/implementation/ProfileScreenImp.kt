package com.stellerbyte.uptodo.services.implementation

import android.net.Uri
import android.util.Log
import com.stellerbyte.uptodo.services.module.ProfileScreenRepo
import com.stellerbyte.uptodo.services.module.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
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
    private val database: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProfileScreenRepo {

    override suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>> = flow {
        val userImageId = UUID.randomUUID()
        val imageName = "images/$userImageId.jpg"
        try {
            emit(Response.Loading)
            val storageRef = storage.reference.child(imageName)
            storageRef.putFile(url).await()
            val downloadUrl = storageRef.downloadUrl.await().toString()
            emit(Response.Success(downloadUrl))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error uploading picture"))
        }
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = callbackFlow {
        try {
            trySendBlocking(Response.Loading)
            auth.signOut()
            trySendBlocking(Response.Success(true))
        } catch (e: Exception) {
            trySendBlocking(Response.Error(e.message ?: "Error signing out"))
        }
        awaitClose { close() }
    }

    override suspend fun createOrUpdateProfileToFirebase(user: UserProfileData): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val username = user.username

            val data = user.copy(id = userId, email = auth.currentUser?.email ?: "", username = username)
            database.collection("users").document(userId).set(data).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error adding or updating profile"))
        }
    }

    override suspend fun loadProfileFromFirebase(): Flow<Response<UserProfileData>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            trySendBlocking(Response.Error("User not authenticated"))
            close()
            return@callbackFlow
        }

        try {
            trySendBlocking(Response.Loading)
            val documentRef = database.collection("users").document(userId)
            val listener = documentRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySendBlocking(Response.Error(error.message ?: "Error fetching profile"))
                    return@addSnapshotListener
                }
                val userProfile = snapshot?.toObject(UserProfileData::class.java)
                if (userProfile != null) {
                    trySendBlocking(Response.Success(userProfile))
                } else {
                    trySendBlocking(Response.Error("Profile data is null"))
                }
            }
            awaitClose { listener.remove() }
        } catch (e: Exception) {
            trySendBlocking(Response.Error(e.message ?: "Error"))
            close()
        }
    }
}
