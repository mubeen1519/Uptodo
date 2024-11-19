package com.stellerbyte.uptodo.components.patterns

import android.util.Patterns
import com.stellerbyte.uptodo.services.implementation.TODOItem
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus


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

fun LocalDate.getNext7Dates(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    repeat(7) { day ->
        dates.add(this.plus(day, DateTimeUnit.DAY))
    }
    return dates
}

fun LocalDate.getPrevious7Dates(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    repeat(7) { day ->
        dates.add(this.minus(day.plus(1), DateTimeUnit.DAY))
    }
    return dates.reversed()
}