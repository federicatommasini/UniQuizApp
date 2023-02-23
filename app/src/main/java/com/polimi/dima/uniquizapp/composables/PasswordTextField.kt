package com.polimi.dima.uniquizapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.grayBackground

@Composable
fun PasswordTextField(field: MutableState<String>, nameField: String, visibility: MutableState<Boolean>){
    TextField(
        value = field.value,
        onValueChange = { field.value = it },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent),
        label = { Text(text = nameField) },
        placeholder = { Text(text = nameField) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(grayBackground, RoundedCornerShape(20.dp)),
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
        }
    )
}