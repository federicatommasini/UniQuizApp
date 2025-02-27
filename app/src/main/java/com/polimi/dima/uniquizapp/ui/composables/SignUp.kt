package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.content.res.Configuration
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
@Composable
fun SignUp(navController: NavController, sharedViewModel: SharedViewModel) {

    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
            .padding(18.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row( modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(whiteBackground)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LogoLandscape()
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        signUpFields(
                            sharedViewModel = sharedViewModel,
                            navController = navController,
                            isInsideAnotherColumn = false
                        )
                    }
                }
            }
            else -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UniQuiz",
                            fontSize = 28.sp,
                            color = customizedBlue,
                            style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.4f)
                        )
                        Box(modifier = Modifier
                            .weight(0.5f)
                            .aspectRatio(1f)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .align(Alignment.Center))
                        }
                    }
                    signUpFields(sharedViewModel = sharedViewModel, navController, true)
                }
            }
        }
            }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun signUpFields(sharedViewModel: SharedViewModel, navController: NavController, isInsideAnotherColumn : Boolean){

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
        modifier = Modifier.then(if(!isInsideAnotherColumn) Modifier.verticalScroll(rememberScrollState()) else Modifier.fillMaxWidth() ),
        horizontalAlignment = Alignment.CenterHorizontally
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
                }), 0.8f)
            CustomSpacer()
            CustomTextField(field = lastNameValue, nameField = "Last Name", Icons.Default.Person,
                lastNameFocusRequester,
                keyboardActions = KeyboardActions(onNext = {
                    usernameFocusRequester.requestFocus()
                }),0.8f)
            CustomSpacer()
            CustomTextField(field = usernameValue, nameField = "Username", customImageVector = Icons.Default.Person,
                usernameFocusRequester,
                keyboardActions = KeyboardActions(onNext = {
                    emailFocusRequester.requestFocus()
                }),0.8f)
            CustomSpacer()
            CustomTextField(field = emailValue, nameField = "Email Address", Icons.Default.Email,
                emailFocusRequester,
                keyboardActions = KeyboardActions(onNext = {
                    universityFocusRequester.requestFocus()
                }),0.8f)
            CustomSpacer()

            DropDownTextField(text = "University", items = items, selectedItem = universityValue.value, onItemSelected = {universityValue.value = it}, universityFocusRequester,0.8f)
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
                    if(!isValidEmail(emailValue.value)) {
                        message.value = "Email not valid"
                    }
                    else if(!checkingVoidField(listOf(firstNameValue, lastNameValue, usernameValue, passwordValue, confirmPasswordValue, emailValue, universityValue))){
                        message.value = "Complete all fields"
                    }
                    else if (passwordValue.value != confirmPasswordValue.value) {
                        message.value =
                            "Password and Confirm Password are different, please retry"
                    } else {
                        val request = RegistrationRequest(
                            username = usernameValue.value,
                            email = emailValue.value,
                            firstName = firstNameValue.value,
                            lastName = lastNameValue.value,
                            password = passwordValue.value,
                            universityName = universityValue.value
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

@Composable
fun CustomSpacer(){
    Spacer(
        modifier = Modifier
            .padding(5.dp)
            .background(whiteBackground)
    )
}

fun checkingVoidField(
    list: List<MutableState<String>>) : Boolean{
    for(el in list){
        if(el.value== ""){
            return false
        }
    }
    return true
}

fun isValidEmail(string : String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return string.matches(emailRegex.toRegex())
}

