package com.polimi.dima.uniquizapp.composables

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
import com.polimi.dima.uniquizapp.data.model.UserViewModel
import com.polimi.dima.uniquizapp.data.di.UserApiModule
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(navController: NavController) {

    val userApi = UserApiModule.provideApi(UserApiModule.provideRetrofit())
    val userRepo = UserRepository(userApi)
    val userViewModel = UserViewModel(userRepo)
    val state by userViewModel.state.collectAsState()
    Log.d("response", state.toString())

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(

        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(whiteBackground)
            .padding(18.dp),
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
                Image(painter = painterResource(id = R.drawable.exam), contentDescription = "")
            }
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
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
                    })
                )
                CustomSpacer()
                PasswordTextField(field = passwordValue,
                    nameField = "Password",
                    visibility = passwordVisibility,
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        //context.doLogin()
                    }),
                    focusRequester = passwordFocusRequester
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        navController.navigate(route = BottomNavItem.Home.screen_route)
                        //context.doLogin()
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
                        color = Color.White,
                        modifier = Modifier.clickable {

                            //navController.popBackStack()  //is it needed? figure it out
                            navController.navigate(Screen.Profile.route){
                                popUpTo(Screen.Profile.route){
                                    inclusive = true
                                }
                            }
                        })
                }
                Spacer(modifier = Modifier.padding(20.dp))

                if(state .isEmpty()){
                    Log.i("Response", "non c'è")
                }
                else{
                    Log.i("Response", "${state[0].firstName}")

                }
                //Text(text = userViewModel.state.collectAsState().value[0].firstName, color = Color.Black)
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

/*
Questa non l'ho capita, ma l'ho copiata la capiremo più avanti
 */
/*
private fun Context.doLogin() {
    Toast.makeText(
        this,
        "Something went wrong, try again later!",
        Toast.LENGTH_SHORT
    ).show()
}
*/