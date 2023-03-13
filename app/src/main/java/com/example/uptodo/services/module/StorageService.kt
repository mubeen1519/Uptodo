package com.example.uptodo.services.module

import com.example.uptodo.services.implementation.TODOItem
import kotlinx.coroutines.flow.Flow

interface StorageService {

    val tasks: Flow<List<TODOItem>>
    suspend fun getTodoItem(todoId: String): TODOItem?

    suspend fun addTodoItem(todoItem: TODOItem): String

    suspend fun updateTodoItem(todoItem: TODOItem)

    suspend fun deleteTodoItem(todoItem: String)

}