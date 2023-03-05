package com.example.uptodo.services.module

import android.net.Uri
import com.example.uptodo.services.implementation.UserProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepo {
    suspend fun uploadPictureToFirebase(url: Uri): Flow<Response<String>>
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun createOrUpdateProfileToFirebase(user: UserProfileData): Flow<Response<Boolean>>
    suspend fun loadProfileFromFirebase(): Flow<Response<UserProfileData>>
}