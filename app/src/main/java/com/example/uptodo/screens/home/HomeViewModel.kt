package com.example.uptodo.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.components.patterns.hasDate
import com.example.uptodo.components.patterns.hasTime
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.EditTodoPage
import com.example.uptodo.navigation.TASK_ID
import com.example.uptodo.screens.category.Icons
import com.example.uptodo.screens.category.Priority
import com.example.uptodo.screens.settings.ThemeSetting
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.services.module.LogService
import com.example.uptodo.services.module.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storageService: StorageService,
    logService: LogService
) : MainViewModel(logService) {

    @Inject
    lateinit var themeSetting: ThemeSetting

    val tasks = storageService.tasks

    var todo = mutableStateOf(TODOItem())



    fun onTodoCheck(todo: TODOItem) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            storageService.updateTodoItem(todo.copy(completed = !todo.completed))
        }
    }

    fun onSaveClick(context: Context) {
        if (todo.value.title.isNotBlank() && todo.value.description.isNotBlank()) {
            viewModelScope.launch(super.showErrorExceptionHandler) {
                val editTask = todo.value
                if (editTask.id.isBlank()) {
                    saveTodo(editTask)
                } else {
                    storageService.updateTodoItem(editTask)
                }
            }
        } else {
            Toast.makeText(context, "Please write more then 3 words", Toast.LENGTH_SHORT).show()
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

    fun onTodoClick(openScreen: (String) -> Unit, todoItem: TODOItem) {
        openScreen("$EditTodoPage?$TASK_ID={${todoItem.id}}")
    }


}