package com.example.uptodo.components.patterns

import android.util.Patterns
import com.example.uptodo.services.implementation.TODOItem
import java.util.regex.Pattern


fun String.isValidEmail() : Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
fun String.idFromParameter(): String {
    return this.substring(1, this.length-1)
}

fun TODOItem?.hasDate():Boolean{
return this?.date.orEmpty().isNotBlank()
}

fun TODOItem?.hasTime():Boolean{
    return this?.time.orEmpty().isNotBlank()
}