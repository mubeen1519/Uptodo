package com.example.uptodo.screens.edittodo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.components.patterns.hasDate
import com.example.uptodo.components.patterns.hasTime
import com.example.uptodo.components.patterns.idFromParameter
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.TASK_DEFAULT_ID
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.services.module.LogService
import com.example.uptodo.services.module.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val storageService: StorageService,
    logService: LogService
) : MainViewModel(logService) {
    val todo = mutableStateOf(TODOItem())

    fun getDateAndTime(todoItem: TODOItem): String {
        val stringBuilder = StringBuilder("")
        if (todoItem.hasDate()) {
            stringBuilder.append(todoItem.date)
            stringBuilder.append(" ")
        }

        if (todoItem.hasTime()) {
            stringBuilder.append("at ")
            stringBuilder.append(todoItem.time)
        }
        return stringBuilder.toString()
    }

    fun getTodo(todoId: String) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            if (todoId != TASK_DEFAULT_ID)
                todo.value = storageService.getTodoItem(todoId.idFromParameter()) ?: TODOItem()
        }
    }

    fun onDelete(todo: TODOItem) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            storageService.deleteTodoItem(todo.id)
        }
    }


}