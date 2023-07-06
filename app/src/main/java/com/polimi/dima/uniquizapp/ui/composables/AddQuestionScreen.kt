package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun AddQuestionScreen(navController: NavController,sharedViewModel: SharedViewModel){

    val quizValue = remember { mutableStateOf("") }
    val newQuizName = remember { mutableStateOf("") }
    val questionText = remember { mutableStateOf("") }
    val quizFocusRequester = remember { FocusRequester() }
    val newQuizNameFocusRequester = remember { FocusRequester() }
    val questionTextFocusRequester = remember { FocusRequester() }
    val answers = remember { mutableListOf(mutableStateOf(""),mutableStateOf(""),mutableStateOf(""),mutableStateOf("")) }
    val answersFocusRequester = remember { mutableListOf(FocusRequester(),FocusRequester(),FocusRequester(),FocusRequester()) }
    val quizzes = sharedViewModel.quizViewModel.getAll(sharedViewModel.subject!!.id)
    val items = mutableListOf<String>()
    for(q in quizzes) { items.add(q.name) }
    items.add("New Quiz")
    items.toList()

    Scaffold(
        topBar = {AppBar(navController = navController,false, true)}
    ){padding ->
        Column(
            modifier = Modifier
                .fillMaxSize().fillMaxHeight()
                .padding(30.dp)
                .background(Color.White)
        ){
            Text("Add a question", color = customizedBlue, fontWeight = FontWeight.Bold, textAlign = TextAlign.Companion.Center, modifier= Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.size(10.dp))
            Column(modifier= Modifier
                .align(Alignment.CenterHorizontally)
                .verticalScroll(rememberScrollState()).fillMaxHeight()) {
                Column(modifier= Modifier
                    .align(Alignment.CenterHorizontally)){
                    DropDownTextField(
                        text = "Quiz",
                        items = items,
                        selectedItem = quizValue.value,
                        onItemSelected = { quizValue.value = it },
                        quizFocusRequester,
                        1f
                    )
                if(quizValue.value == "New Quiz"){
                    Spacer(modifier = Modifier.size(10.dp))
                    CustomTextField(
                        field = newQuizName,
                        nameField = "Argument Name",
                        customImageVector = null,
                        focusRequester = newQuizNameFocusRequester,
                        keyboardActions = KeyboardActions(onNext = {
                            newQuizNameFocusRequester.requestFocus()
                        }),
                        fraction = 1f
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                CustomTextField(
                    field = questionText,
                    nameField = "Question text",
                    customImageVector = null,
                    focusRequester = questionTextFocusRequester,
                    keyboardActions = KeyboardActions(onNext = {
                        questionTextFocusRequester.requestFocus()
                    }),
                    fraction = 1f
                )
                }

                if(questionText.value!=""){
                    Spacer(modifier = Modifier.size(10.dp))

                    Text("Insert the possible answers and check the correct one: ", color = customizedBlue,modifier = Modifier.align(Alignment.CenterHorizontally))

                    val checkedState = remember { mutableListOf(mutableStateOf(false),mutableStateOf(false),mutableStateOf(false),mutableStateOf(false)) }
                    for(i in 0..3){

                        Spacer(modifier = Modifier.size(10.dp))

                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                            CustomTextField(
                                field = answers[i],
                                nameField = "Answer text",
                                customImageVector = null,
                                focusRequester = questionTextFocusRequester,
                                keyboardActions = KeyboardActions(onNext = {
                                    answersFocusRequester[i].requestFocus()
                                }),
                                fraction = 0.8f
                            )
                            Checkbox(
                                checked = checkedState[i].value,
                                onCheckedChange = {
                                    checkedState[i].value = it
                                    if(it)
                                        for(j in 0..3)
                                            if(j!=i){
                                                checkedState[j].value = false
                                            }
                                },
                                colors =  CheckboxDefaults.colors(
                                    checkedColor = customizedBlue
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Box( contentAlignment = Alignment.BottomCenter, modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Add question", fontWeight = FontWeight.Bold)
                    }
                }



            }

        }
    }
}