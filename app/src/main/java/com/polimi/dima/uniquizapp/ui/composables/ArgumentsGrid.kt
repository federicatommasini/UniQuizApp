package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArgumentsGrid(subject: Subject, sharedViewModel: SharedViewModel, route: String, navController: NavController){

    sharedViewModel.quizViewModel.getAll(subject!!.id)
    val state by sharedViewModel.quizViewModel.allQuizzesState.collectAsState()

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 250.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 70.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(state){ item ->
                Card(
                    onClick = { /*navController.navigate(route = route + item.id)*/ },
                    shape = RoundedCornerShape(30),
                    backgroundColor = customLightGray,
                    modifier = Modifier
                        .padding(6.dp)
                        .height(100.dp)
                        .width(100.dp)
                ){
                    Box() {
                        Text(
                            text = item.name,
                            lineHeight = 25.sp,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(
                            Alignment.Center).padding(2.dp)
                        )
                    }
                }
            }
        })
}
