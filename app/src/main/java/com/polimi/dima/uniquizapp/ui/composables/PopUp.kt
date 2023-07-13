package com.polimi.dima.uniquizapp.ui.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun PopUp(title: String,
              text:String,
              buttonText: String,
              isPopupVisible: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { isPopupVisible.value = false },
        title = {
            Text(text = title)
        },
        text = {
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