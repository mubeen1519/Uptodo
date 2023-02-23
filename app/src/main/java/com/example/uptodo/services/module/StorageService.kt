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
    fun getTodoItem(todoId: String, onError: (Throwable) -> Unit, onSuccess: (TODOItem) -> Unit)

    fun addTodoItem(todoItem: TODOItem, onResult: (Throwable?) -> Unit)

    fun updateTodoItem(todoItem: TODOItem, onResult: (Throwable?) -> Unit)

    fun deleteTodoItem(todoItem: String, onResult: (Throwable?) -> Unit)

    fun getAllTodoFromFireBase(
        onResult: (Throwable?) -> Unit,
        onSuccess: (List<TODOItem>) -> Unit
    )

    fun addTodoListener(
        todo: String,
        onDocumentEvent: (Boolean, TODOItem) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun removeListener()


    fun addImage(
        bitmap: Bitmap, onSuccess: (String, String) -> Unit = { _, _ -> },
        onFailure: (String) -> Unit
    )


}