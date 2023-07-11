package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.widget.TextViewCompat.AutoSizeTextType
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun LogoLandscape() {

    Box(modifier = Modifier.fillMaxHeight(0.1f)
        .fillMaxWidth()){
        Text(
            text = "UniQuiz",
            fontSize = 50.sp,
            color = customizedBlue,
            style = TextStyle(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(
                    Alignment.Center,
                    unbounded = true
                )
        )
    }

    Box(modifier = Modifier
        .fillMaxWidth(0.7f)
        .aspectRatio(1f)
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(1f))//.align(Alignment.BottomCenter))
    }
}