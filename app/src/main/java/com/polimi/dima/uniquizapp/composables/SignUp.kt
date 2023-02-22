package com.polimi.dima.uniquizapp.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground

import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground

@Composable
fun SignUpPage() {

    val usernameValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val universityValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

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
                CustomTextField(field = usernameValue, nameField = "Username", Icons.Default.Person)
                CustomSpacer()
                CustomTextField(field = emailValue, nameField = "Email Address", Icons.Default.Email)
                CustomSpacer()
                CustomTextField(field = universityValue, nameField = "University", Icons.Default.School)
                CustomSpacer()
                PasswordTextField(field = passwordValue, nameField = "Password", visibility = passwordVisibility)
                CustomSpacer()
                PasswordTextField(field = confirmPasswordValue, nameField = "Confirm Password", visibility = confirmPasswordVisibility)
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {   /*TODO*/  },
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
                    modifier = Modifier.clickable(onClick = {/*TODO*/ })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}


@Composable
fun CustomTextField(field: MutableState<String>, nameField: String, customImageVector: ImageVector){
    TextField(
        value = field.value,
        onValueChange = { field.value = it },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent),
        label = { Text(text = nameField) },
        placeholder = { Text(text = nameField) },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(grayBackground, RoundedCornerShape(20.dp)),
        trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null)}
    )
}

@Composable
fun PasswordTextField(field: MutableState<String>, nameField: String, visibility: MutableState<Boolean>){
    TextField(
        value = field.value,
        onValueChange = { field.value = it },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent),
        label = { Text(text = nameField) },
        placeholder = { Text(text = nameField) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(grayBackground, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        visualTransformation = if (visibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (visibility.value) {
                IconButton(
                    onClick = {
                        visibility.value = false
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = null)
                }
            } else {
                IconButton(
                    onClick = {
                        visibility.value = true
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = null)
                }
            }
        }
    )
}

@Composable
fun CustomSpacer(){
    Spacer(
        modifier = Modifier
            .padding(5.dp)
            .background(whiteBackground))
}

