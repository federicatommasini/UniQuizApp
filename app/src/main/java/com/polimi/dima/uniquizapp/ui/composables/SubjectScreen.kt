package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun SubjectScreen(navController: NavController, subjectId: String?, sharedViewModel: SharedViewModel) {

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    val subject = sharedViewModel.subjectViewModel.getSubjectById(subjectId!!)
    sharedViewModel.addSubject(subject!!)

    sharedViewModel.quizViewModel.getAll(subject!!.id)
    val subjectQuizzes by sharedViewModel.quizViewModel.allQuizzesState.collectAsState()

    Scaffold(
        topBar = {AppBar(navController = navController,false, true,sharedViewModel)}
    ){padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ){
            SubjectBar(subjectId = subjectId!!, sharedViewModel = sharedViewModel, navController= navController)

            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = "Arguments of this subject",
                fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }
}
