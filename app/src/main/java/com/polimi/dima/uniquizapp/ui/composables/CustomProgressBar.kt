package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground

@Composable
fun CustomProgressBar(progress: Float) {

    // Progress Bar
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(10.dp)
    ) {
        // for the background of the ProgressBar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(9.dp))
                .border(1.dp, customizedBlue, RoundedCornerShape(9.dp))
                .background(whiteBackground)
        )
        // for the progress of the ProgressBar
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(9.dp))
                .background(customizedBlue)
                .animateContentSize()
        )
    }
}



