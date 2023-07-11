package com.polimi.dima.uniquizapp.ui.composables

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.BottomNavItem
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(navController: NavController, sharedViewModel: SharedViewModel) {


    val rememberedUserViewModel = remember { sharedViewModel.userViewModel }

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val message = remember { mutableStateOf("") }

    val context = LocalContext.current
    val passwordFocusRequester = remember {FocusRequester()}
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(whiteBackground)
            .padding(18.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "UniQuiz",
                    fontSize = 32.sp,
                    color = customizedBlue,
                    style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                )
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
            }
        }
        Column(
            //modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Login",
                fontSize = 38.sp,
                style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CustomTextField(field = emailValue,
                    nameField = "Email Address",
                    Icons.Default.Email,
                    focusRequester,
                    keyboardActions = KeyboardActions(onNext = {
                        passwordFocusRequester.requestFocus()
                    }),
                    fraction = 0.8f
                )
                CustomSpacer()
                PasswordTextField(field = passwordValue,
                    nameField = "Password",
                    visibility = passwordVisibility,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    focusRequester = passwordFocusRequester
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        sharedViewModel.logout()
                        Log.d("request", emailValue.value + " "+ passwordValue.value)
                        val loginReq = LoginRequest(emailValue.value, passwordValue.value)
                        val user =  runBlocking {rememberedUserViewModel.login(loginReq)}
                        if (user != null){
                            message.value = ""
                            sharedViewModel.addUser(user)
                            navController.navigate(BottomNavItem.Home.screen_route){
                                popUpTo(Screen.Login.route){
                                    inclusive = true
                                }
                            }
                        }
                        else {
                            message.value = "Wrong credentials, retry!"
                        }
                        keyboardController?.hide()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(58.dp)
                        .padding(start = 60.dp, end = 60.dp)
                ) {
                    Text(text = "Login",
                        fontSize = 28.sp,
                        color = Color.White)
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = message.value,
                    color = Color.Red)
                Text(
                    text = "Create An Account",
                    modifier = Modifier.clickable {
                        navController.navigate(route = Screen.SignUp.route)}
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}
