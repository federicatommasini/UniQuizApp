package com.polimi.dima.uniquizapp.ui.composables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Ranking(subject: Subject, sharedViewModel: SharedViewModel, navController: NavController){

    var user = sharedViewModel.user
    val map : Map<String, Integer> = subject.ranking
    val sortedList = map.entries.sortedByDescending{it.value as Int}
    var count =0

    LazyVerticalGrid(columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(sortedList){ item ->
                for((i,value) in sortedList.withIndex()){
                    if(item.equals(value))
                        count = i+1
                }
                val user = sharedViewModel.userViewModel.getUserById(item.key)
                val painter = rememberImagePainter(
                    if(user.profilePicUrl != "") {user.profilePicUrl}
                    else {R.drawable.ic_user})
                Card(
                    onClick = { },
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, customLightGray),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Box(contentAlignment = Alignment.Center,modifier = Modifier.weight(0.3f)){
                            Card(
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .align(Alignment.Center),
                            ) {
                                Image(
                                    painter = painter, contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(0.05f))
                        Text(text=user.username, modifier = Modifier.weight(0.45f))
                        Spacer(modifier = Modifier.weight(0.05f))
                        Text(text = map.get(item.key).toString(), modifier = Modifier.weight(0.1f))
                        Spacer(modifier = Modifier.weight(0.05f))
                        Text(text = count.toString() + "\u00B0", fontSize = 28.sp,
                            color = customizedBlue,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(2.dp)
                                .wrapContentHeight())
                        Spacer(modifier = Modifier.weight(0.05f))
                    }
                }
            }
        })
}
