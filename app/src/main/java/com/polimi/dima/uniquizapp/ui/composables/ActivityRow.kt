package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActivityRow(paddingValues: PaddingValues, header : String, text : String, fractionCompleted : Float?, progressBar : Boolean) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = header,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Left,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(end = 5.dp)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = text,
                fontSize = 20.sp,
                textAlign = TextAlign.Right,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(start = 5.dp)

            )
        }
    }
    if(progressBar){
        CustomProgressBar(progress = fractionCompleted!!)
    }

}