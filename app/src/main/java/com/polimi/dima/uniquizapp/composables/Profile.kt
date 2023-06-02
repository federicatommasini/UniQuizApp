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
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
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
            .padding(10.dp)
            .size(300.dp))
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
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                //.focusRequester(focusRequester ?: FocusRequester()),
                //trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null) },
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
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                    //.focusRequester(usernameFocusRequester ?: FocusRequester()),
                //trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null) },
                //keyboardActions = keyboardActions
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
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent),
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
                                passwordVisibility.value = false
                            },
                        ) {
                            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = null)
                        }
                    } else {
                        IconButton(
                            onClick = {
                                passwordVisibility.value = true
                            },
                        ) {
                            Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = null)
                        }
                    }
                },
                //.focusRequester(focusRequester ?: FocusRequester()),
                //trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null) },
                //keyboardActions = keyboardActions
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
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                //.focusRequester(focusRequester ?: FocusRequester()),
                //trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null) },
                //keyboardActions = keyboardActions
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
                onClick = { isEditable = !isEditable },
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


@Composable
fun ProfileImage() {

    val imageUri = rememberSaveable { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? -> uri?.let { imageUri.value = it.toString() }
    }
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
        Card(shape = CircleShape,
            modifier = Modifier
                .padding(50.dp)
                .size(140.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    painter = painter, contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize(),
                    //.clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(0.dp)
                        .align(Alignment.BottomEnd),
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
        }
        Text(text = "Name", fontSize = 26.sp,
            color = whiteBackground,
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp))
    }
}