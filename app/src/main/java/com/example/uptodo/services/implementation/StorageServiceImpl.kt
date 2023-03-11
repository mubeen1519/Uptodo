package com.example.uptodo.services.implementation

import com.example.uptodo.services.module.AccountService
import com.example.uptodo.services.module.StorageService
import com.example.uptodo.services.module.trace
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : AccountService
) : StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks: Flow<List<TODOItem>>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection("user").document(user.id).collection(
                TODO_COLLECTION).snapshots().map { snapshots -> snapshots.toObjects() }
        }


    override suspend  fun getTodoItem(
        todoId: String,
    ):TODOItem? =
        firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .document(todoId)
            .get()
            .await()
            .toObject()



    override suspend fun addTodoItem(
        todoItem: TODOItem,
    ): String =
        trace(SAVE_TASK_TRACE) {
            firestore
                .collection("user")
                .document(Firebase.auth.currentUser?.uid!!)
                .collection(TODO_COLLECTION)
                .add(todoItem)
                .await()
                .id
        }

    override suspend fun updateTodoItem(
        todoItem: TODOItem,
    ): Unit =
        trace(UPDATE_TASK_TRACE) {
            firestore
                .collection("user")
                .document(Firebase.auth.currentUser?.uid!!)
                .collection(TODO_COLLECTION)
                .document(todoItem.id)
                .set(todoItem)
                .await()
        }

    override suspend fun deleteTodoItem(
        todoItem: String,
    ) {
        firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .document(todoItem)
            .delete()
            .await()
    }


    override suspend fun getAllTodoFromFireBase(
        onResult: (Throwable?) -> Unit,
        onSuccess: (List<TODOItem>) -> Unit
    ) {
        firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .get()
            .addOnCompleteListener { if (it.exception != null) onResult(it.exception) }
            .addOnSuccessListener {
                val todo = it.toObjects<TODOItem>()
                onSuccess(todo)
            }.await()

    }

    companion object {
        private const val TODO_COLLECTION = "todo"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}

