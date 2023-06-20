package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun SubjectScreen(navController: NavController, subjectId: String?, sharedViewModel: SharedViewModel) {

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    val subject = sharedViewModel.subjectViewModel.getSubjectByName(subjectId!!)
    var present : Boolean = false
    sharedViewModel.quizViewModel.getAll(subject!!.id)
    val subjectQuizzes by sharedViewModel.quizViewModel.allQuizzesState.collectAsState()

    Scaffold(
        topBar = {AppBar(navController = navController)}
    ){padding ->
        Column(
            modifier = Modifier
                .imePadding()
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ){
            Spacer(modifier = Modifier.padding(15.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .align(Alignment.Start)) {
                Text(
                    text = subjectId.toString(),
                    fontWeight = FontWeight.Bold,
                    color = customizedBlue,
                    modifier = Modifier.weight(0.8f),
                    textAlign = TextAlign.Start,
                    fontSize = 30.sp
                )
                for( s in userSubjectState)
                    if(s.equals(subject))
                        present = true;
                if(!present){
                    Button(
                        onClick = {
                            val user = sharedViewModel.userViewModel.addSubjectToUser(subject!!,sharedViewModel.user!!.id)
                            sharedViewModel.addUser(user)
                        },
                        shape = CircleShape,
                        modifier = Modifier.weight(0.2f)
                    ) {
                        // Inner content including an icon and a text label
                        Icon(
                            imageVector = Icons.Default.Add,
                            //TODO this should be a plus if the subject is not already in the user's list
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = "Arguments of this subject",
                fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )

            /*val quizzesNames =  mutableListOf<String>()*/
            if(subjectQuizzes[0] != null) {
                /*for (quiz in subjectQuizzes)
                    quizzesNames.add(quiz.name)*/
                ArgumentsGrid(
                    state = mutableStateOf(subjectQuizzes),
                    route = "",
                    navController = navController
                )
            }
        }
    }
}
