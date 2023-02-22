package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polimi.dima.uniquizapp.ui.screens.SignUpPage
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ){
                    Login()
                }*/
                SignUp()
                //trial()
            }
        }
    }
}

/*@Preview
@Composable
fun trial(){
    SignUpPage()
}*/

@Composable
fun Login(){
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            painter = painterResource(id = R.drawable.visibility),
            null,
            Modifier.size(80.dp),
            tint = Color.White
        )
        TextInput()
        TextInput()
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sign In", Modifier.padding(vertical = 8.dp))
            
        }
    }
}

@Composable
fun TextInput(){
    var value by remember { mutableStateOf("") }
    TextField(value = value, onValueChange = {value = it})
}


/*@Composable
fun SignUp(){

    val usernameValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val universityValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ){
            //image
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(38.dp))
                .background(whiteBackground)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Sign Up",
                    fontSize = 38.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                )
            }
            Spacer(modifier = Modifier.padding(28.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                OutlinedTextField(value = usernameValue.value,
                    onValueChange = {usernameValue.value = it},
                    label = {Text(text = "Username")},
                    placeholder = { Text(text = "Username")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                OutlinedTextField(
                    value = emailValue.value,
                    onValueChange = {emailValue.value = it},
                    label = {Text(text = "Email Address")},
                    placeholder = { Text(text = "Email Address")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f))
                OutlinedTextField(
                    value = universityValue.value,
                    onValueChange = {universityValue.value = it},
                    label = {Text(text = "University")},
                    placeholder = { Text(text = "University")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f))
                OutlinedTextField(
                    value = passwordValue.value,
                    onValueChange = {passwordValue.value = it},
                    label = {Text(text = "Password")},
                    placeholder = { Text(text = "Password")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }){} /*{
                           Icon(
                               ImageVector = vectorResource(id = R.drawable.password_eye),
                               tint = if(passwordVisibility.value) primaryColor else Color.Gray)
                       }*/
                    },
                    visualTransformation = if(passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                OutlinedTextField(
                    value = confirmPasswordValue.value,
                    onValueChange = {confirmPasswordValue.value = it},
                    label = {Text(text = "Confirm Password")},
                    placeholder = { Text(text = "Confirm Password")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                        }) {
                            /*Icon(
                                ImageVector = vectorResource(id = R.drawable.password_eye),
                                tint = if(confirmPasswordVisibility.value) primaryColor else Color.Gray)*/
                        }
                    },
                    visualTransformation = if(confirmPasswordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(58.dp)) {
                    Text(text = "Sign Up", fontSize = 28.sp)
                }
            }

        }
    }


}*/

@Preview(showBackground = true)
@Composable
fun SignUp(){
    SignUpPage()
}
