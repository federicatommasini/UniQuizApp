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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.data.model.Answer
import com.polimi.dima.uniquizapp.data.model.NewQuestionRequest
import com.polimi.dima.uniquizapp.data.model.Question
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddQuestionScreen(navController: NavController,sharedViewModel: SharedViewModel){

    val quizValue = remember { mutableStateOf("") }
    val newQuizName = remember { mutableStateOf("") }
    val questionText = remember { mutableStateOf("") }
    val quizFocusRequester = remember { FocusRequester() }
    val newQuizNameFocusRequester = remember { FocusRequester() }
    val questionTextFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val answers = remember { mutableListOf(mutableStateOf(""),mutableStateOf(""),mutableStateOf(""),mutableStateOf("")) }
    val answersFocusRequester = remember { mutableListOf(FocusRequester(),FocusRequester(),FocusRequester(),FocusRequester()) }
    val message = remember { mutableStateOf("") }
    val subjectId = sharedViewModel.subject!!.id
    val quizzes = sharedViewModel.quizViewModel.getAll(subjectId)
    val items = mutableListOf<String>()
    val checkedState = remember { mutableListOf(mutableStateOf(false),mutableStateOf(false),mutableStateOf(false),mutableStateOf(false)) }

    var showAlert by remember { mutableStateOf(false) }

    for(q in quizzes) { items.add(q.name) }
    items.add("New Quiz")
    items.toList()

    Scaffold(
        topBar = {AppBar(navController = navController,false, true,sharedViewModel, false,null)}
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
                        focusRequester = quizFocusRequester,
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
                            questionTextFocusRequester.requestFocus()
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
                        answersFocusRequester[0].requestFocus()
                    }),
                    fraction = 1f
                )
                }

                if(questionText.value!="") {
                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        "Insert the possible answers and check the correct one: ",
                        color = customizedBlue,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    for (i in 0..2) {

                        Spacer(modifier = Modifier.size(10.dp))

                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            CustomTextField(
                                field = answers[i],
                                nameField = "Answer text",
                                customImageVector = null,
                                focusRequester = answersFocusRequester[i],
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        answersFocusRequester[i + 1].requestFocus()
                                    }, onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }),
                                fraction = 0.8f
                            )
                            Checkbox(
                                checked = checkedState[i].value,
                                onCheckedChange = {
                                    checkedState[i].value = !checkedState[i].value
                                    if (checkedState[i].value)
                                        for (j in 0..3)
                                            if (j != i) {
                                                checkedState[j].value = false
                                            }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = customizedBlue
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        CustomTextField(
                            field = answers[3],
                            nameField = "Answer text",
                            customImageVector = null,
                            focusRequester = answersFocusRequester[3],
                            keyboardActions = KeyboardActions(onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }),
                            fraction = 0.8f
                        )
                        Checkbox(
                            checked = checkedState[3].value,
                            onCheckedChange = {
                                checkedState[3].value = !checkedState[3].value
                                if (checkedState[3].value)
                                    for (j in 0..3)
                                        if (j != 3) {
                                            checkedState[j].value = false
                                        }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = customizedBlue
                            )
                        )
                    }
                }


                Spacer(modifier = Modifier.size(20.dp))
                Box( contentAlignment = Alignment.BottomCenter, modifier = Modifier
                    .fillMaxWidth().fillMaxHeight()
                ) {
                    Button(
                        onClick = {
                        if((quizValue.value=="New Quiz" &&  !checkingVoidField(listOf(quizValue, newQuizName, questionText, answers[0], answers[1], answers[2], answers[3])))
                            || (quizValue.value!=="New Quiz" && !checkingVoidField(listOf(quizValue, questionText, answers[0], answers[1], answers[2], answers[3])))) {
                            message.value = "Complete all fields to add"
                            showAlert = true
                        }else if(quizValue.value=="New Quiz" && items.contains(newQuizName.value)) {
                            message.value = "A quiz with this name already exists"
                            showAlert = true
                        }else if(!checkedState[0].value && !checkedState[1].value && !checkedState[2].value && !checkedState[3].value) {
                            message.value = "Tick the correct answer!"
                            showAlert = true
                        }else {
                            message.value = ""
                            var list: MutableList<Answer> = mutableListOf()
                            for( (index,a) in answers.withIndex())
                                list.add(Answer( answers[index].value, checkedState[index].value))
                            val question = Question(questionText.value,list, listOf())
                            var quizId : String? = null
                            for(q in quizzes){
                                if(q.name==quizValue.value){
                                    quizId=q.id
                                    break
                                }
                            }
                            val request = NewQuestionRequest(sharedViewModel.user!!.id,subjectId,quizId,newQuizName.value,question)
                            val subject = runBlocking{sharedViewModel.quizViewModel.addQuestion(request)}
                            if (subject != null) {
                                sharedViewModel.addSubject(subject)
                                var newUser = sharedViewModel.userViewModel.getUserById(sharedViewModel.user!!.id)
                                sharedViewModel.addUser(newUser)
                                navController.navigate(Screen.AddQuestionScreen.route) {
                                    popUpTo(Screen.AddQuestionScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                        colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue, contentColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Add question", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.padding(50.dp))
                    if(showAlert){
                        Box(modifier = Modifier.align(Alignment.BottomCenter)){
                            AutoDismissAlert(
                                message = message.value,
                                durationInSeconds = 3,
                                onDismiss = { showAlert = false }
                            )
                        }

                    }
                }



            }

        }
    }
}