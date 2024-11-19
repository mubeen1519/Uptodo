package com.stellerbyte.uptodo.screens.edittodo

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.stellerbyte.uptodo.components.patterns.hasDate
import com.stellerbyte.uptodo.components.patterns.hasTime
import com.stellerbyte.uptodo.components.patterns.idFromParameter
import com.stellerbyte.uptodo.mainViewModel.MainViewModel
import com.stellerbyte.uptodo.navigation.TASK_DEFAULT_ID
import com.stellerbyte.uptodo.screens.category.Icons
import com.stellerbyte.uptodo.screens.category.Priority
import com.stellerbyte.uptodo.services.implementation.TODOItem
import com.stellerbyte.uptodo.services.module.LogService
import com.stellerbyte.uptodo.services.module.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val storageService: StorageService,
    logService: LogService
) : MainViewModel(logService) {
    val todo = mutableStateOf(TODOItem())

    fun onTodoCheck(todo: TODOItem) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            storageService.updateTodoItem(todo.copy(completed = !todo.completed))
        }
    }

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
    fun onSaveClick(context: Context) {
        if (todo.value.title.isNotBlank() && todo.value.description.isNotBlank()) {
            viewModelScope.launch(super.showErrorExceptionHandler) {
                val editTask = todo.value
                if (editTask.id.isBlank()) {
                    saveTodo(editTask)
                } else {
                    updateTodo(editTask)
                }
            }
        } else {
            Toast.makeText(context, "Please write more then 3 words", Toast.LENGTH_SHORT).show()
        }
    }


    fun updateTodo(todo: TODOItem){
        viewModelScope.launch {
            storageService.updateTodoItem(todo)
        }
    }

    private fun saveTodo(todoItem: TODOItem) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            storageService.addTodoItem(todoItem)
        }
    }


    fun onTitleChange(newValue: String) {
        todo.value = todo.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        todo.value = todo.value.copy(description = newValue)
    }

    fun onPriorityChange(priority: Priority) {
        todo.value.priority = priority
    }

    fun onIconChange(icons: Icons) {
        todo.value.icon = icons
    }

    fun onDateChange(newDate: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = newDate
        val newDateValue = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).format(calendar.time)
        todo.value = todo.value.copy(date = newDateValue)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        todo.value = todo.value.copy(time = newDueTime)
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }
}