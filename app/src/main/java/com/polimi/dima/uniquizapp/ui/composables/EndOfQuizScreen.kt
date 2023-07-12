package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.BottomNavItem
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun EndOfQuizScreen (navController: NavController, sharedViewModel: SharedViewModel){

    val points = sharedViewModel.points
    val quiz = sharedViewModel.quiz
    val subject = sharedViewModel.subject!!
    val score : String =  points.toString() + "/" + quiz!!.questions.size.toString()
    val text = if (points <= quiz!!.questions.size/2) "You can do better! You scored: " + score
               else "Congratulations! You scored: " + score


    Scaffold(
        topBar = {AppBar(navController = navController,false,true,sharedViewModel, false,null)},
    ){ padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(1f)
                .padding(30.dp)
                .background(Color.White)
        ) {
            Text(text, textAlign = TextAlign.Center, color = customizedBlue, fontSize = 30.sp, modifier = Modifier.weight(0.1f).fillMaxWidth())
            Box(modifier = Modifier.weight(0.8f).fillMaxWidth().align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center){
                if(points <= quiz!!.questions.size/2){
                    Image(painter = painterResource(id = R.drawable.fail_image), contentDescription = "QuizFailImage", modifier = Modifier.align(Alignment.Center))
                }
                else{
                    Image(painter = painterResource(id = R.drawable.pass_image), contentDescription = "QuizPassImage", modifier = Modifier.align(Alignment.Center))
                }
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(0.1f)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(route = "subject_screen/" + subject.id){
                            sharedViewModel.resetPoints()
                            popUpTo(Screen.SubjectScreen.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(0.4f)
                        .sizeIn(minHeight = 50.dp)
                ) {
                    Text("Go back to Quizzes", color = Color.White, textAlign = TextAlign.Center)
                }
                Spacer( modifier = Modifier.weight(0.2f))
                Button(
                    onClick = {
                        navController.navigate(route = "home"){
                            sharedViewModel.resetPoints()
                            popUpTo(BottomNavItem.Home.screen_route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(0.4f)
                        .sizeIn(minHeight = 50.dp)
                ) {
                    Text("Go to Home", color = Color.White, textAlign = TextAlign.Center)
                }
            }

        }


    }
}