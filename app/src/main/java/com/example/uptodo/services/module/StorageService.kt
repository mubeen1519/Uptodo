package com.example.uptodo.services.module

import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.services.implementation.UserProfileData
import kotlinx.coroutines.flow.Flow

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


}