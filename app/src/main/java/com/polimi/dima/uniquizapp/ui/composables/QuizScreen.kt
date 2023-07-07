package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customGreen
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customRed
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.greenBorder
import com.polimi.dima.uniquizapp.ui.theme.redBorder
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
@Composable
fun QuizScreen(navController: NavController, quizId: String?, questionId: Int?,  sharedViewModel: SharedViewModel){

    val quiz = sharedViewModel.quizViewModel.getQuizById(quizId!!)
    sharedViewModel.addQuiz(quiz!!)
    val question = quiz!!.questions[questionId!!]
    var selected  = remember { mutableStateListOf<Boolean>(false,false,false,false) }
    var check = remember { mutableStateOf(false)}
    var correct = remember { mutableStateOf(false)}
    Scaffold(
        topBar = {AppBar(navController = navController,false, true,sharedViewModel)},
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Color.White)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .border(3.dp, Color.Gray, RoundedCornerShape(30.dp))
                    .background(color = customLightGray, shape = RoundedCornerShape(30.dp))
            ) {
                Text(
                    text = question.content,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }

            for((index,answer) in question.answers.withIndex()) {
                Spacer(modifier = Modifier.weight(0.3f))
                OutlinedButton(onClick = {
                    if(!check.value){
                        val newSelected = selected.toMutableList() // Create a copy of the list
                        newSelected[index] = !newSelected[index]
                        for ((i, s) in newSelected.withIndex()) {
                            if (newSelected[index] && i != index && s) {
                                newSelected[i] = false
                            }
                        }
                        selected.clear() // Clear the original list
                        selected.addAll(newSelected)}
                 },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =
                        if(!check.value || (!selected[index] && !answer.correct))
                            customLightGray
                        else if(check.value && answer.correct)
                            customGreen
                        else customRed
                    ),
                    border = BorderStroke( width =if (selected[index]) 2.5.dp else 0.dp, color=
                    if(!check.value || (!selected[index] && !answer.correct))
                        Color.Gray
                    else if(check.value && answer.correct)
                        greenBorder
                    else redBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures { selected[index] = !selected[index] }
                        }
                        .graphicsLayer {
                            scaleX = if (selected[index]) 1.05f else 1f
                            scaleY = if (selected[index]) 1.05f else 1f
                        }
                ) {
                    Text(
                        text = answer.text,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    "Question " + (questionId!!+1) + "/" + quiz!!.questions.size,
                    color = customizedBlue,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Button(
                    onClick = {
                        if(selected.contains(true)){
                            if(!check.value){
                                check.value = true
                            }
                            else if(questionId!! < quiz.questions.size-1){
                                if(question.answers[selected.indexOf(true)].correct)
                                    sharedViewModel.addPoint()
                                navController.navigate(route = "quiz/" + quizId + "/"+ (questionId!!+1)){
                                    popUpTo(Screen.QuizScreen.route){
                                        inclusive = true
                                    }
                                }
                            }
                            else navController.navigate(route = "endOfQuiz"){
                                if(question.answers[selected.indexOf(true)].correct){
                                    sharedViewModel.addPoint()
                                }
                                sharedViewModel.quizViewModel.addScore(quizId!!, sharedViewModel.user!!.id, sharedViewModel.points)
                                sharedViewModel.subjectViewModel.updateRanking(sharedViewModel.subject!!.id,sharedViewModel.user!!.id)
                                popUpTo(Screen.QuizScreen.route){
                                    inclusive = true
                                }
                            }
                          }
                      },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.CenterEnd).sizeIn(minWidth = 70.dp)
                ) {
                    if(!check.value)
                        Text("Check", color = Color.White)
                    else Text("Next", color = Color.White)
                }
            }

        }

    }
}