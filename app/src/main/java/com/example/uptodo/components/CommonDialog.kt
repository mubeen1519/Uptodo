package com.example.uptodo.components

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun CommonDialog(
    state : MutableState<Boolean>,
    content : @Composable () -> Unit,
){
    AlertDialog(onDismissRequest = { state.value = false}, buttons = { content()})
}