package com.example.uptodo.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.uptodo.mainViewModel.MainViewModel
import com.example.uptodo.navigation.DEFAULT_TODO_ID
import com.example.uptodo.screens.category.Icons
import com.example.uptodo.screens.category.Priority
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.LogService
import com.example.uptodo.services.module.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val logService: LogService
) : MainViewModel(logService) {
    private var todoItem = mutableStateMapOf<String, TODOItem>()
    var allUserTodo = mutableStateListOf<TODOItem>()

    var todo = mutableStateOf(TODOItem())


    fun onTodoCheck(todo: TODOItem) {
        storageService.updateTodoItem(todo.copy(completed = !todo.completed)) { error ->
            if (error != null) onError(error)
        }
    }

//    fun addListener(){
//        viewModelScope.launch(super.showErrorExceptionHandler){
//            storageService.addTodoListener(UUID.randomUUID().toString(), ::onDocumentEvent, ::onError)
//        }
//    }
//
//    fun removeListener(){
//        viewModelScope.launch(super.showErrorExceptionHandler){
//            storageService.removeListener()
//        }
//    }

    private fun onDocumentEvent(wasDocumentDeleted : Boolean, todos: TODOItem){
        if(wasDocumentDeleted) this.todoItem.remove(todos.id) else this.todoItem[todos.id] = todos
    }

    fun onSaveClick() {
            viewModelScope.launch(super.showErrorExceptionHandler){
                    val editTask = todo.value
                    saveTodo(editTask)
            }
    }

    private fun updateTodo(todoItem: TODOItem) {
        viewModelScope.launch(super.showErrorExceptionHandler) {
            val updateTodo = todoItem.copy()
            storageService.updateTodoItem(updateTodo) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun saveTodo(todoItem: TODOItem) {
        storageService.addTodoItem(todoItem) { error ->
            if (error != null) onError(error)

        }
    }

    fun onDelete(todo: TODOItem){
        viewModelScope.launch(super.showErrorExceptionHandler){
            storageService.deleteTodoItem(todo.id){ error ->
                if(error != null) {
                    onError(error)
                } else {
                    allUserTodo.removeIf{ it.id == todo.id}
                }
            }
        }
    }

    fun initailizeTodo(){
        viewModelScope.launch(super.showErrorExceptionHandler){
            storageService.getAllTodoFromFireBase(
                onResult = { if(null != it) onError(it)},
                onSuccess = {
                    allUserTodo.clear()
                    allUserTodo.addAll(it)
                }
            )
        }
    }

    fun getTodo(todoId : String){
        viewModelScope.launch(super.showErrorExceptionHandler){
            if(todoId != DEFAULT_TODO_ID){
                storageService.getTodoItem(todoId, ::onError){
                    todo.value = it
                }
            }
        }
    }

    fun onTitleChange(newValue : String){
        todo.value =todo.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue : String){
        todo.value =todo.value.copy(description = newValue)
    }

    fun onPriorityChange(priority: Priority){
        todo.value.priority = priority
    }

    fun onIconChange(icons: Icons){
        todo.value.icon = icons
    }

}