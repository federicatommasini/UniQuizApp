package com.polimi.dima.uniquizapp.composables

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground

@Composable
fun Profile(navController: NavController) {

    var notification = rememberSaveable { mutableStateOf("") }

    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var isEditable by remember { mutableStateOf(false) }

    var username by rememberSaveable { mutableStateOf("Username") }
    var university by rememberSaveable { mutableStateOf("University") }
    var password by rememberSaveable { mutableStateOf("Password") }
    var email by rememberSaveable { mutableStateOf("Email") }

    //var imageUri by remember { mutableStateOf<Uri?>(null) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

     val customizedColors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
            .verticalScroll(rememberScrollState())
            .padding(0.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(customizedBlue)
            .size(290.dp)
            .clip(shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)) //not working
            //.clip(RoundedCornerShape(36.dp)) does not work
            .padding(0.dp))
        {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Text(text = "Profile",
                    fontSize = 32.sp,
                    color = whiteBackground,
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ))
            }
            ProfileImage()
        }

        CustomSpacer()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(
                value = username, //here we should pass the username of the user
                onValueChange = { username = it },
                colors = customizedColors,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),

                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                enabled = isEditable
            )
        }
        CustomSpacer()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(text = "University", modifier = Modifier.width(100.dp))
            TextField(
                value = university,
                onValueChange = { university = it },
                colors = customizedColors,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp))
                    .border(0.dp, Color.Transparent, RoundedCornerShape(20.dp)), //does not work
                enabled = isEditable
            )
        }
        CustomSpacer()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(text = "Password", modifier = Modifier.width(100.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                colors = customizedColors,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                visualTransformation = if (passwordVisibility.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                    },
                trailingIcon = {
                    if (passwordVisibility.value) {
                        IconButton(
                            onClick = {
                                passwordVisibility.value = false },
                        ) {
                            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = null)
                        }
                    } else {
                        IconButton(
                            onClick = {
                                passwordVisibility.value = true },
                        ) {
                            Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = null)
                        }
                    }
                },
                enabled = isEditable
            )
        }
        CustomSpacer()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(text = "Email", modifier = Modifier.width(100.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                colors = customizedColors,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                enabled = false
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Button(
                onClick = { isEditable = !isEditable
                          /* TO DO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                shape = RoundedCornerShape(20.dp),
                content = {
                    Text(text = if (isEditable) "Save" else "Edit", color = whiteBackground)
                }
            )
        }
        // Use the isEditable state variable to control the enabled/disabled state of the fields

        }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileImage() {

    val imageUri = rememberSaveable { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? -> uri?.let { imageUri.value = it.toString() }
    }

    var showDialog by remember { mutableStateOf(false) }

    val painter = rememberImagePainter(
        if(imageUri.value.isEmpty()){
            R.drawable.ic_user
        }
        else{
            imageUri.value
        }
    )

    Column(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
        //verticalArrangement = Arrangement.Top)

    {
        Spacer(modifier = Modifier.padding(30.dp))
        Box(modifier = Modifier
            .size(140.dp, 140.dp)){
            Card(shape = CircleShape,
                modifier = Modifier
                    .padding(0.dp)
                    .size(140.dp)
                    .align(Alignment.Center),
                //.clickable { showDialog = true }
            ) {
                    Image(
                        painter = painter, contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable { showDialog = true},
                        //.clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            modifier = Modifier.wrapContentSize(Alignment.Center),
                            buttons = {
                                Button(onClick = { showDialog = false }) {
                                    Text(text = "Close")
                                }
                            }
                        ) /*{ Image(
                            painter = imageResource,
                            contentDescription = "Full-screen Image"
                        )
                    }*/
                    }
            }
            IconButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .size(40.dp)
                    .padding(0.dp)
                    .align(Alignment.BottomEnd),
                //.align(Alignment.BottomEnd),
                content = {
                    Icon(
                        Icons.Default.PhotoCamera,
                        contentDescription = "Edit Icon",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.White, CircleShape)
                            .padding(4.dp)
                    )
                }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "Name", fontSize = 26.sp,
                color = whiteBackground,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp))
        }
        }





}
/*
Column(){
    Box(){
       Card(){
           Image(){}
       }
        IconButton(){

        }
    }
    Row(){
        "Name"
    }
}
 */

