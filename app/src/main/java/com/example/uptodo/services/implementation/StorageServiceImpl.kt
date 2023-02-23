package com.example.uptodo.services.implementation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.result.ActivityResultLauncher
import androidx.customview.widget.Openable
import com.example.uptodo.services.module.StorageService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.net.URI
import java.util.UUID
import javax.inject.Inject

class StorageServiceImpl @Inject constructor() : StorageService {
    private var listenerRegistration: ListenerRegistration? = null

    val storage = Firebase.storage.reference.child("images");

    override fun addTodoListener(
        todo: String,
        onDocumentEvent: (Boolean, TODOItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val query = Firebase.firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .whereEqualTo(TODO_ID, todo)

        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {
                val wasDocumentDeleted = it.type == DocumentChange.Type.REMOVED
                val todo = it.document.toObject<TODOItem>().copy(id = it.document.id)
                onDocumentEvent(wasDocumentDeleted, todo)
            }
        }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }

    override fun addImage(
        bitmap: Bitmap,
        onSuccess: (String, String) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }


    override fun getTodoItem(
        todoId: String,
        onError: (Throwable) -> Unit,
        onSuccess: (TODOItem) -> Unit
    ) {
        Firebase
            .firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .document(todoId)
            .get()
            .addOnFailureListener { error -> onError(error) }
            .addOnSuccessListener { result ->
                val task = result.toObject<TODOItem>()?.copy(id = result.id)
                onSuccess(task ?: TODOItem())
            }
    }

    override fun addTodoItem(
        todoItem: TODOItem,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase
            .firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .add(todoItem)
            .addOnFailureListener { error -> onResult(error) }
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun updateTodoItem(
        todoItem: TODOItem,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase
            .firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .document(todoItem.id)
            .set(todoItem)
            .addOnFailureListener { error -> onResult(error) }
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun deleteTodoItem(
        todoItem: String,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase
            .firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .document(todoItem)
            .delete()
            .addOnFailureListener { error -> onResult(error) }
            .addOnCompleteListener { onResult(it.exception) }
    }


    override fun getAllTodoFromFireBase(
        onResult: (Throwable?) -> Unit,
        onSuccess: (List<TODOItem>) -> Unit
    ) {
        Firebase
            .firestore
            .collection("user")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection(TODO_COLLECTION)
            .get()
            .addOnCompleteListener { if (it.exception != null) onResult(it.exception) }
            .addOnSuccessListener {
                val todo = it.toObjects<TODOItem>()
                onSuccess(todo)
            }
            .addOnFailureListener { onResult(it) }

    }

    companion object {
        private const val TODO_ID = "todoId"
        private const val STORE_COLLECTION = "store"
        private const val TODO_COLLECTION = "todo"

    }
}

