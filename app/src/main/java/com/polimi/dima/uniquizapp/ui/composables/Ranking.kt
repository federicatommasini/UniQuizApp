package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import org.apache.commons.logging.Log

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Ranking(subject: Subject, sharedViewModel: SharedViewModel, navController: NavController){

    var user = sharedViewModel.user
    val state by sharedViewModel.userViewModel.allUsersState.collectAsState()
    val map = subject.ranking.toSortedMap()
    val stateMap by remember {mutableStateOf(subject.ranking.toSortedMap())}

    LazyVerticalGrid(columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(map.keys.toList()){ item ->
                val user = sharedViewModel.userViewModel.getUserById(item)
                Card(
                    onClick = { },
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, customLightGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Box(modifier = Modifier.weight(0.3f)){
                            ProfileImage(user = user, sharedViewModel = sharedViewModel,false)
                        }
                        Spacer(modifier = Modifier.weight(0.05f))
                        Text(text=user.firstName + user.lastName + map.get(item).toString(), modifier = Modifier.weight(0.65f))
                    }
                }
            }
        })

}
