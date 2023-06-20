package com.polimi.dima.uniquizapp.ui.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customLightGray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyGrid(state: MutableState<List<String>>, route: String, navController: NavController) {

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 70.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(state.value){ item ->
                Card(
                    onClick = { navController.navigate(route = route + item) },
                    shape = RoundedCornerShape(30),
                    backgroundColor = customLightGray,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(80.dp)
                        .width(100.dp)
                ){
                    Box() {
                        Text(text = item,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        })
}