package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun EndOfQuizScreen (navController: NavController){
    Scaffold(
        topBar = {AppBar(navController = navController)},
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Button(
                    onClick = {
                        //TODO
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(0.5f).fillMaxHeight(0.5f)
                ) {
                    Text("Go back to Quizzes", color = Color.White)
                }
                Button(
                    onClick = {
                       //TODO
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.BottomEnd).fillMaxWidth(0.5f).fillMaxHeight(0.5f)
                ) {
                    Text("Go to Home", color = Color.White)
                }
            }

        }


    }
}