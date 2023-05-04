package com.polimi.dima.uniquizapp.composables

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Alignment
import com.polimi.dima.uniquizapp.ui.theme.customLightGray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyGrid(state: MutableState<List<String>>) {

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(state.value){ item ->
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(30),
                    backgroundColor = customLightGray,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(80.dp)
                        .width(100.dp)
                ){
                    Text(text = item, Modifier.align(Alignment.Center))
                }
            }
            /*items(state.value) {
                Card(
                    backgroundColor = customizedBlue,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                ) {
                    Text(
                        text = "boh",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }*/
        })
}