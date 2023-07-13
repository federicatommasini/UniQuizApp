package com.polimi.dima.uniquizapp.ui.composables

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun AutoDismissAlert(
    message: String,
    durationInSeconds : Int,
    onDismiss: () -> Unit
){

    LaunchedEffect(Unit) {
        delay(TimeUnit.SECONDS.toMillis(durationInSeconds.toLong()))
        onDismiss()
    }

    Snackbar (
        modifier = Modifier.padding(16.dp)
    ){
        Text(text = message)
    }
}