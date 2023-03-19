package com.example.uptodo.services.implementation

import com.example.uptodo.screens.category.Colors
import com.example.uptodo.screens.category.Icons
import com.example.uptodo.screens.category.Priority
import com.google.firebase.firestore.DocumentId

data class TODOItem(
    @DocumentId val id : String = "",
    val title : String = "",
    var icon : Icons? = null,
    var description : String = "",
    var date : String = "",
    var time : String = "",
    var completed : Boolean = false,
    var priority: Priority? = null,
    var colors: Colors? = null

)