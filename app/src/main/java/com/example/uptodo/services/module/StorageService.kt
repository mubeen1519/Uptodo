package com.example.uptodo.services.module

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.services.implementation.UserProfileData
import kotlinx.coroutines.flow.Flow
import java.net.URI

interface StorageService {

    val tasks: Flow<List<TODOItem>>
    suspend fun getTodoItem(todoId: String) : TODOItem?

    suspend fun addTodoItem(todoItem: TODOItem) : String

    suspend fun updateTodoItem(todoItem: TODOItem)

    suspend fun deleteTodoItem(todoItem: String)

    suspend fun getAllTodoFromFireBase(
        onResult: (Throwable?) -> Unit,
        onSuccess: (List<TODOItem>) -> Unit
    )

//    fun addTodoListener(
//        userId: String,
//        onDocumentEvent: (Boolean, TODOItem) -> Unit,
//        onError: (Throwable) -> Unit
//    )
//
//    fun removeListener()



}