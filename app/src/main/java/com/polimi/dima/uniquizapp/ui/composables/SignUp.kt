package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.focus.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.BottomNavItem
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground

import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.data.model.RegistrationRequest
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.runBlocking

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SignUp(navController: NavController, sharedViewModel: SharedViewModel) {

    val firstNameValue = remember { mutableStateOf("") }
    val lastNameValue = remember { mutableStateOf("") }
    val usernameValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val universityValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val universityFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val universities = sharedViewModel.uniViewModel.getAllUni()
    val items = mutableListOf<String>()
    for(u in universities) { items.add(u.name) }
    items.toList()

    val rememberedUserViewModel = remember { sharedViewModel.userViewModel }
    val message = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(whiteBackground)
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "UniQuiz",
                    fontSize = 32.sp,
                    color = customizedBlue,
                    style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                )
                Image(painter = painterResource(id = R.drawable.exam), contentDescription = "")
            }
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Sign Up",
                fontSize = 38.sp,
                style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CustomTextField(field = firstNameValue, nameField = "First Name", Icons.Default.Person,
                    firstNameFocusRequester,
                    keyboardActions = KeyboardActions(onNext = {
                        lastNameFocusRequester.requestFocus()
                    }))
                CustomSpacer()
                CustomTextField(field = lastNameValue, nameField = "Last Name", Icons.Default.Person,
                    lastNameFocusRequester,
                    keyboardActions = KeyboardActions(onNext = {
                        usernameFocusRequester.requestFocus()
                    }))
                CustomSpacer()
                CustomTextField(field = usernameValue, nameField = "Username", customImageVector = Icons.Default.Person,
                    usernameFocusRequester,
                    keyboardActions = KeyboardActions(onNext = {
                        emailFocusRequester.requestFocus()
                    }))
                CustomSpacer()
                CustomTextField(field = emailValue, nameField = "Email Address", Icons.Default.Email,
                    emailFocusRequester,
                    keyboardActions = KeyboardActions(onNext = {
                        universityFocusRequester.requestFocus()
                    }))
                CustomSpacer()

                DropDownTextField(items = items, selectedItem = universityValue.value, onItemSelected = {universityValue.value = it}, universityFocusRequester)
                CustomSpacer()
                PasswordTextField(field = passwordValue, nameField = "Password", visibility = passwordVisibility,
                    keyboardActions = KeyboardActions(onNext = {
                        confirmPasswordFocusRequester.requestFocus()
                    }),
                    focusRequester = passwordFocusRequester)
                CustomSpacer()
                PasswordTextField(field = confirmPasswordValue, nameField = "Confirm Password", visibility = confirmPasswordVisibility,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        //context.doLogin()
                    }),
                    focusRequester = confirmPasswordFocusRequester)
                CustomSpacer()
                Text(
                    text = message.value,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.8f))
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        Log.d("confirm", passwordValue.value + " " + confirmPasswordValue.value)
                        if(!checkingVoidField(firstNameValue, lastNameValue, usernameValue, passwordValue, confirmPasswordValue, emailValue, universityValue)){
                            message.value = "Complete all fields"
                        }
                        else if (passwordValue.value != confirmPasswordValue.value) {
                            message.value =
                                "Password and Confirm Password are different, please retry"

                        } else {
                            val request = RegistrationRequest(
                                usernameValue.value,
                                emailValue.value,
                                firstNameValue.value,
                                lastNameValue.value,
                                passwordValue.value,
                                universityValue.value
                            )
                            val user = runBlocking { rememberedUserViewModel.register(request) }
                            if (user != null) {
                                sharedViewModel.addUser(user)
                                navController.navigate(BottomNavItem.Home.screen_route) {
                                    popUpTo(Screen.Profile.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(58.dp)
                        .padding(start = 60.dp, end = 60.dp)
                ) {
                    Text(text = "Sign Up", fontSize = 28.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Login Instead",
                    modifier = Modifier.clickable {
                        //navController.popBackStack()
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.Login.route){
                                inclusive = true
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}

@Composable
fun CustomSpacer(){
    Spacer(
        modifier = Modifier
            .padding(5.dp)
            .background(whiteBackground)
    )
}

fun checkingVoidField(
    firstNameValue : MutableState<String>,
    lastNameValue : MutableState<String>,
    usernameValue : MutableState<String>,
    passwordValue : MutableState<String>,
    emailValue : MutableState<String>,
    confirmPasswordValue : MutableState<String>,
    universityValue : MutableState<String>) : Boolean{
    if(firstNameValue.value == ""){
        return false
    }
    if(lastNameValue.value == ""){
        return false
    }
    if(usernameValue.value == "") {
        return false
    }
    if(passwordValue.value == ""){
        return false
    }
    if(confirmPasswordValue.value == ""){
        return false
    }
    if(emailValue.value == ""){
        return false
    }
    if(universityValue.value == ""){
        return false
    }
    return true
}

