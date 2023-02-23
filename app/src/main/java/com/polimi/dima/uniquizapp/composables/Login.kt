package com.polimi.dima.uniquizapp.composables

/*import androidx.compose.foundation.*

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.learnandroid.loginapplication.R
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.whiteBackground
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.primaryColor
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground


@Composable
fun Login(navController: NavController) {

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {

            Image(painter = painterResource(id = R.drawable.exam), null)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(RoundedCornerShape(topLeft = 30.dp, topRight = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "Email Address") },
                        placeholder = { Text(text = "Email Address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        onImeActionPerformed = { _, _ ->
                            focusRequester.requestFocus()
                        }
                    )

                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = vectorResource(id = R.drawable.password_eye),
                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        label = { Text("Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester = focusRequester),
                        onImeActionPerformed = { _, controller ->
                            controller?.hideSoftwareKeyboard()
                        }

                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "Sign In", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Create An Account",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("register_page"){
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }


            }
        }

    }
}*/