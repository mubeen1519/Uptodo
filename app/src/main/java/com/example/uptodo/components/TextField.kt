package com.example.uptodo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.uptodo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholderText: String,
    value: String = "",
    onFieldChange: (String) -> Unit,
    isFieldSecured: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(true)
    }
    if (label != null) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.alpha(0.87f),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    Spacer(modifier = modifier.padding(8.dp))
    OutlinedTextField(
        value = value, onValueChange = onFieldChange,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodySmall
            )
        },
        textStyle =MaterialTheme.typography.bodySmall,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = Color.Gray,
            containerColor = MaterialTheme.colorScheme.secondary,
            textColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }
        ),
        visualTransformation = if (isFieldSecured && isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isFieldSecured) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if (isPasswordVisible) Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                        contentDescription = "Visibility OFF"
                    ) else Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_red_eye_24),
                        contentDescription = "Visibility ON"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showClearButton by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }


    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                showClearButton = (it.isFocused)
            }
            .focusRequester(focusRequester)
            .alpha(0.8f)
            .border(
                BorderStroke(
                    1.dp, Color.White
                ), shape = RoundedCornerShape(5.dp)
            ),
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        placeholder = {
            Text(text = stringResource(id = R.string.searchFieldText), color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodySmall)
        },

        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            textColor = MaterialTheme.colorScheme.onSurface,
            placeholderColor = MaterialTheme.colorScheme.onSurface
        ),
        textStyle = MaterialTheme.typography.bodySmall,
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { state.value = TextFieldValue("") }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }),

        )
}