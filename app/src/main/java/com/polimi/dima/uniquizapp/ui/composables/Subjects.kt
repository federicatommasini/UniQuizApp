package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun Subjects(navController: NavController, sharedViewModel: SharedViewModel) {

    sharedViewModel.subjectViewModel.getState()
    val allSubjectState by sharedViewModel.subjectViewModel.allSubjectsState.collectAsState()
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()

    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false,null)},
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Spacer(modifier = Modifier.padding(15.dp))
                CustomSearchBar(allSubjectState,navController)
                Spacer(modifier = Modifier.padding(15.dp))
                Text(
                    text = "Your Subjects",
                    fontWeight = FontWeight.Bold,
                    color = customizedBlue,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                if(userSubjectState.isEmpty()) {
                    Box(modifier = Modifier
                        .fillMaxSize(0.8f)
                        .align(Alignment.CenterHorizontally),){
                        Text(
                            text = "Search your subjects to add them!",
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth(0.8f),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,

                            )
                    }

                }
                LazyGrid(
                    route = "subject_screen/",
                    navController,
                    sharedViewModel
                )
            }
        }
    )
}