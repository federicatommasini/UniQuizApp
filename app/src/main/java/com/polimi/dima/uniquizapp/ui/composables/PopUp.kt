package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun PopUp(title: String,
          text:String,
          buttonText: String,
          isPopupVisible: MutableState<Boolean>,
          isFeedback: Boolean,
          sharedViewModel: SharedViewModel,
          quizId: String?,
          index: Int?
){
    val field = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester()}
    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = { isPopupVisible.value = false },
        title = {
            Text(text = title)
        },
        text = {
            if(isFeedback){
                TextField(
                    value = field.value,
                    onValueChange = { field.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent),
                    label = { },
                    placeholder = { Text(text = "message", textAlign = TextAlign.Start) },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth().height(100.dp)
                        .background(grayBackground, RoundedCornerShape(20.dp))
                        .focusRequester(focusRequester ?: FocusRequester()),
                    trailingIcon = { },
                    keyboardActions = KeyboardActions(onNext = {
                        focusRequester.requestFocus()
                    })
                )
            }

            Text(text = text)
        },
        confirmButton = {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isPopupVisible.value = false
                            if(isFeedback){
                                var user = sharedViewModel.quizViewModel.addReport(quizId!!, index!!, sharedViewModel.user!!.id, field.value)
                                sharedViewModel.addUser(user!!)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                        shape = CircleShape
                    ) {
                        Text(text = buttonText)
                    }
                }
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = true),
        modifier = Modifier.background(Color.White, RoundedCornerShape(20.dp))
    )
}