package com.polimi.dima.uniquizapp.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground
import org.w3c.dom.Text


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(navController: NavController) {

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
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
                        //context.doLogin()
                    }),
                    focusRequester = passwordFocusRequester
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        //context.doLogin()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(58.dp)
                        .padding(start = 60.dp, end = 60.dp)
                ) {
                    Text(text = "Login", fontSize = 28.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Create An Account",
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate("register_page") {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    })
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
@Composable
fun Login() {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current


    ProvideWindowInsets {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                context.doLogin()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("SIGN IN", Modifier.padding(vertical = 8.dp))
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?", color = Color.White)
                TextButton(onClick = {}) {
                    Text("SING UP")
                }
            }
        }
    }
}
*/