package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.grayBackground

@Composable
fun PasswordTextField(field: MutableState<String>,
                      nameField: String,
                      visibility: MutableState<Boolean>,
                      focusRequester: FocusRequester,//? = null
                      keyboardActions: KeyboardActions){

    val customKeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = false,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    )

    TextField(
        value = field.value,
        onValueChange = { field.value = it },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent),
        label = { Text(text = nameField) },
        placeholder = { Text(text = nameField) },
        singleLine = true,
        keyboardOptions = customKeyboardOptions,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(grayBackground, RoundedCornerShape(20.dp))
            .focusRequester(focusRequester ?: FocusRequester()),
    shape = RoundedCornerShape(20.dp),
        visualTransformation = if (visibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (visibility.value) {
                IconButton(
                    onClick = {
                        visibility.value = false
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = null)
                }
            } else {
                IconButton(
                    onClick = {
                        visibility.value = true
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = null)
                }
            }
        },
        keyboardActions = keyboardActions
    )
}