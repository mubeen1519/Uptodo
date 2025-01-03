package com.stellerbyte.uptodo.services.implementation

import com.stellerbyte.uptodo.services.module.AccountService
import com.stellerbyte.uptodo.services.module.StorageService
import com.stellerbyte.uptodo.services.module.trace
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks: Flow<List<TODOItem>>
        get() = auth.currentUser.flatMapLatest { user ->
            if (user.id.isEmpty()) {
                flowOf(emptyList()) // Safely return empty list when no user
            } else {
                currentCollection(user.id).snapshots().map { snapshots -> snapshots.toObjects() }
            }
        }


    override suspend fun getTodoItem(
        todoId: String
    ): TODOItem? {
        val userId = auth.currentUserId
        require(userId.isNotEmpty()) { "User ID is required to fetch a todo item" }
        return currentCollection(auth.currentUserId).document(todoId).get().await().toObject()
    }


    override suspend fun addTodoItem(
        todoItem: TODOItem,
    ): String =
        trace(SAVE_TASK_TRACE) {
            currentCollection(auth.currentUserId)
                .add(todoItem)
                .await()
                .id
        }

    override suspend fun updateTodoItem(
        todoItem: TODOItem,
    ): Unit =
        trace(UPDATE_TASK_TRACE) {
            currentCollection(auth.currentUserId)
                .document(todoItem.id)
                .set(todoItem)
                .await()
        }

    override suspend fun deleteTodoItem(
        todoItem: String,
    ) {
        currentCollection(auth.currentUserId)
            .document(todoItem)
            .delete()
            .await()
    }

    private fun currentCollection(uid: String): CollectionReference {
        if (uid.isEmpty()) {
            throw IllegalStateException("User ID cannot be null or empty when accessing Firestore collections.")
        }
        return firestore.collection("users").document(uid).collection(TODO_COLLECTION)
    }

    companion object {
        private const val TODO_COLLECTION = "todo"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}

