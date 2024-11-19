package com.stellerbyte.uptodo.services.implementation

import com.stellerbyte.uptodo.screens.category.Icons
import com.stellerbyte.uptodo.screens.category.Priority
import com.google.firebase.firestore.DocumentId

data class TODOItem(
    @DocumentId val id : String = "",
    val title : String = "",
    var icon : Icons? = null,
    var description : String = "",
    var date : String = "",
    var reminderDateAndTime : String? = null,
    var time : String = "",
    var completed : Boolean = false,
    var priority: Priority? = null,
)