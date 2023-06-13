package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController){
    CenterAlignedTopAppBar(
    title = { Text("UniQuizApp",
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        color = Color.White,
        modifier = Modifier.wrapContentSize(Alignment.Center))},
    modifier = Modifier.background(customizedBlue),
    actions = {
        IconButton(onClick = { navController.navigate(Screen.Profile.route)}) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Default.Person,
                contentDescription = ""
            )
        }
    },
    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = customizedBlue)
    )
}
/*
@Composable
fun AppBar(){
    TopAppBar {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxWidth(0.33f))
            Text(
                text = "UniQuizApp",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(0.33f)
                    .weight(0.33f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            Row(
                ) {
                IconButton(modifier = Modifier.padding(end = 0.dp), onClick = {

                }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "profile icon")
                }
            }
        }
    }
}*/

