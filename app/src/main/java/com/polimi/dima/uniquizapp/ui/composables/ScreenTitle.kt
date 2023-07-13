package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue

@Composable
fun ScreenTitle(text: String){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(customizedBlue)
            .padding(end = 20.dp, start = 20.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = customLightGray,
            modifier = Modifier
                .weight(0.8f)
                .padding(top = 8.dp, bottom = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
    }
    Divider(color = customizedBlue, thickness = 2.dp)
}