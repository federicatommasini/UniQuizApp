package com.polimi.dima.uniquizapp.ui.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyGrid(route: String, navController: NavController, sharedViewModel: SharedViewModel) {

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val subjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 16.dp,
            end = 20.dp,
            bottom = 70.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(subjectState){ item ->
                Card(
                    onClick = { navController.navigate(route = route + item.id) },
                    shape = RoundedCornerShape(30),
                    backgroundColor = customLightGray,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(100.dp)
                        .width(120.dp)
                ){
                    Box() {
                        Text(text = item.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        })
}