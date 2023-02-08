package com.example.uptodo.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uptodo.ui.theme.InputColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label : String? = null,
    placeholderText : String,
    value : String = "",
    onFieldChange : (String) -> Unit,
    isFieldSecured : Boolean = false,
){
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(true)
    }
    if (label != null){
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.alpha(0.87f),
            color = Color.White
        )
    }
    Spacer(modifier = modifier.padding(8.dp))
    OutlinedTextField(
        value = value, onValueChange = onFieldChange,
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 10.sp
            )
        },
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = Color.Gray,
            containerColor = InputColor,
            textColor = Color.White,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus()}
        ),
        visualTransformation = if(isFieldSecured && isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if(isFieldSecured){
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if(isPasswordVisible) Icon(
                        painter = painterResource(id = com.example.uptodo.R.drawable.ic_baseline_visibility_off_24),
                        contentDescription = "Visibility OFF"
                    ) else Icon(
                        painter = painterResource(id = com.example.uptodo.R.drawable.ic_baseline_remove_red_eye_24),
                        contentDescription = "Visibility ON"
                    )
                }
            }
        }
    )
}