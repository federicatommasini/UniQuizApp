package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.composables.TestTags.ARGUMENT_TEXT
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArgumentsGrid(subject: Subject, sharedViewModel: SharedViewModel, route: String, navController: NavController){

    sharedViewModel.quizViewModel.getAll(subject!!.id)
    val state by sharedViewModel.quizViewModel.allQuizzesState.collectAsState()
    val isPopupVisible = remember { mutableStateOf(false) }

    LazyVerticalGrid(columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier.background(Color.White),
        content = {
            items(state){ item ->
                Card(
                    onClick = {
                        if(sharedViewModel.user!!.subjectIds.contains(subject.id))
                            navController.navigate(route = route + item.id + "/"+0){
                                popUpTo(Screen.QuizScreen.route){
                                    inclusive = true
                                }
                            }
                        else isPopupVisible.value=true
                    },
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, customLightGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp).testTag(ARGUMENT_TEXT)
                ){
                    Box() {
                        Text(
                            text = item.name,
                            lineHeight = 25.sp,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .padding(2.dp)
                        )
                        if(isPopupVisible.value){
                            PopUp(title = "Add the subject before!", text = "You have to add ${subject.name} to your subjects using the button above, then you will be able to  start exercising using quizzes!",
                                buttonText = "Okay", isPopupVisible =isPopupVisible )
                        }
                    }
                }
            }
        })

}
