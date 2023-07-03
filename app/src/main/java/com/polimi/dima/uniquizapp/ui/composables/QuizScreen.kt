package com.polimi.dima.uniquizapp.ui.composables

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.ui.theme.customGray
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun QuizScreen(navController: NavController, quizId: String?, questionId: Int?,  sharedViewModel: SharedViewModel){

    val quiz = sharedViewModel.quizViewModel.getQuizById(quizId!!)
    val question = quiz!!.questions[questionId!!]
    var selected  = remember { mutableStateListOf<Boolean>(false,false,false,false) }
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
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .border(2.dp, customizedBlue, RoundedCornerShape(30.dp))
                    .background(color = customLightGray, shape = RoundedCornerShape(30.dp))
            ) {
                Text(
                    text = question.content,
                    textAlign = TextAlign.Justify,
                )
            }

            for((index,answer) in question.answers.withIndex()) {
                Spacer(modifier = Modifier.weight(0.3f))

                Button(
                    onClick = {
                        val newSelected = selected.toMutableList() // Create a copy of the list
                        newSelected[index] = !newSelected[index]
                        for ((i, s) in newSelected.withIndex()) {
                            if (newSelected[index] && i != index && s) {
                                newSelected[i] = false
                            }
                        }
                        selected.clear() // Clear the original list
                        selected.addAll(newSelected)
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = customLightGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f).pointerInput(Unit) {
                            detectTapGestures { selected[index] = !selected[index] }
                        }
                        .graphicsLayer {
                            scaleX = if (selected[index]) 1.05f else 1f
                            scaleY = if (selected[index]) 1.05f else 1f
                        }.border(
                            width = if (selected[index]) 2.dp else 0.dp,
                            customizedBlue,
                            RoundedCornerShape(30.dp)
                        )
                ) {
                    Text(
                        text = answer.text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text("Check", color = Color.White)
                }
            }

        }

    }
}