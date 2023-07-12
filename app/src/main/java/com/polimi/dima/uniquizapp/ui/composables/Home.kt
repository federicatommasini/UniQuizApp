package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun Home(navController: NavController, sharedViewModel: SharedViewModel){

    val user = sharedViewModel.user
    val totScore = totScoreUser(user!!.id, sharedViewModel)
    val listSubjectsOfUser = sharedViewModel.subjectViewModel.getSubjectsByUser(user!!.id)
    val completedQuizUser = completedQuizUser(sharedViewModel, user!!.id)
    val totQuizUser = totQuizUser(listSubjectsOfUser, sharedViewModel)
    var fractionQuiz : Float
    if(completedQuizUser != 0 && totQuizUser != 0){
        fractionQuiz = completedQuizUser.toFloat()/totQuizUser.toFloat()
    }
    else{
        fractionQuiz = 0f
    }
    val completedSubjectsUser = completedSubjectsUser(sharedViewModel, user!!.id)
    val totSubjectsUser = listSubjectsOfUser.size
    val fractionSubject : Float
    if(completedSubjectsUser != 0 && totSubjectsUser != 0){
        fractionSubject = completedSubjectsUser.toFloat()/totSubjectsUser.toFloat()
    }
    else{
        fractionSubject = 0f
    }
    val rowWithBarPadding = PaddingValues(start = 0.dp, top = 20.dp, end = 0.dp, bottom = 5.dp)
    val middleRowPadding = PaddingValues(start = 0.dp, top = 20.dp, end = 0.dp, bottom = 10.dp)
    val lastRowPadding = PaddingValues(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 20.dp)

    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false, null)},
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Welcome ${user?.username} to the UniQuiz app!",
                fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(30.dp)
                    .fillMaxSize().padding(30.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
            Text(
                text = "With this app you can study for your university exams and learning new topics using fun quizzes!",
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 0.dp, horizontal = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier
                    .border(2.dp, customizedBlue, RoundedCornerShape(40.dp))
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Your Score:",
                    fontSize = 26.sp,
                    color = customizedBlue,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 2.dp)
                )
                Text(
                    text = "$totScore!",
                    fontSize = 26.sp,
                    color = customizedBlue,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Activities",
                    fontSize = 26.sp,
                    color = customizedBlue,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            Column(
            ) {
                ActivityRow(
                    paddingValues = rowWithBarPadding,
                    header = "Quizzes Completed",
                    text = "$completedQuizUser out of $totQuizUser",
                    fractionCompleted = fractionQuiz,
                    progressBar = true)
                ActivityRow(
                    paddingValues = rowWithBarPadding,
                    header = "Subjects Completed",
                    text = "$completedSubjectsUser out of $totSubjectsUser",
                    fractionCompleted = fractionSubject,
                    progressBar = true
                )
                ActivityRow(
                    paddingValues = middleRowPadding,
                    header = "Questions Added",
                    text = user!!.questionsAdded.toString(),
                    fractionCompleted = null,
                    progressBar = false
                )
                ActivityRow(
                    paddingValues = lastRowPadding,
                    header = "Questions Reported",
                    text = user!!.questionsReported.toString(),
                    fractionCompleted = null,
                    progressBar = false
                )
            }
        }
    }
}

fun totScoreUser(userId : String, sharedViewModel : SharedViewModel) : Int {
    return sharedViewModel.userViewModel.getPoints(userId);
}

fun completedSubjectsUser(sharedViewModel: SharedViewModel, userId: String): Int {
    return sharedViewModel.subjectViewModel.completedSubjectsUser(userId)
}

fun completedQuizUser(sharedViewModel: SharedViewModel, userId: String) : Int {
    return (sharedViewModel.quizViewModel.getQuizzesCompletedByUser(userId))!!.size
}

fun totQuizUser(subjects : List<Subject>, sharedViewModel: SharedViewModel): Int {
    var totQuizzes = 0
    for(subject in subjects){
        totQuizzes += subject.quizIds.size
    }
    return totQuizzes;
}