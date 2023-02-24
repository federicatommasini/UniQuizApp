package com.polimi.dima.uniquizapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.polimi.dima.uniquizapp.ui.theme.grayBackground

@Composable
fun CustomTextField(field: MutableState<String>,
                    nameField: String,
                    customImageVector: ImageVector,
                    focusRequester: FocusRequester,//? = null,
                    keyboardActions: KeyboardActions){
    TextField(
        value = field.value,
        onValueChange = { field.value = it },
        colors = TextFieldDefaults.textFieldColors(
        unfocusedIndicatorColor = Color.Transparent),
        label = { Text(text = nameField) },
        placeholder = { Text(text = nameField) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(grayBackground, RoundedCornerShape(20.dp))
            .focusRequester(focusRequester ?: FocusRequester()),
        trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null) },
        keyboardActions = keyboardActions
    )
}