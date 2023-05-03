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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.grayBackground

@Composable
fun Profile(navController: NavController) {

    var notification = rememberSaveable { mutableStateOf("") }

    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var isEditable by remember { mutableStateOf(false) }

    var username by rememberSaveable() { mutableStateOf("Username") }
    var university by rememberSaveable() { mutableStateOf("University") }
    var test1 by rememberSaveable() { mutableStateOf("test1") }
    var test2 by rememberSaveable() { mutableStateOf("test2") }


    //var imageUri by remember{ mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(whiteBackground)
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
        //.imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            //.background(customizedBlue),
            horizontalArrangement = Arrangement.SpaceBetween,
            //verticalAlignment = Alignment.Top
        ) {
            Text(text = "Cancel",
                modifier = Modifier.clickable { notification.value = "Cancelled" })
            Text(text = "Save",
                modifier = Modifier.clickable { notification.value = "Profile updated" })
            /*Text(text = "Profile",
                fontSize = 32.sp,
                color = whiteBackground,
                style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            )*/
        }


        ProfileImage()

        Button(
            onClick = { isEditable = !isEditable },
            content = {
                Text(text = if (isEditable) "Save" else "Edit")
            }
        )
        // Use the isEditable state variable to control the enabled/disabled state of the fields
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
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
                    .fillMaxWidth(0.8f)
                    .background(grayBackground, RoundedCornerShape(20.dp)),
                //.focusRequester(focusRequester ?: FocusRequester()),
                //trailingIcon = { Icon(imageVector = customImageVector, contentDescription = null) },
                //keyboardActions = keyboardActions
                enabled = isEditable
            )
        }


        /*horizontalArrangement = Arrangement.Center)*/
        /*Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        /*horizontalArrangement = Arrangement.Center)*/
        {
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }*/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        /*horizontalArrangement = Arrangement.Center)*/
        {
            Text(text = "University", modifier = Modifier.width(100.dp))
            TextField(
                value = university,
                onValueChange = { university = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        /*horizontalArrangement = Arrangement.Center)*/
        {
            Text(text = "test", modifier = Modifier.width(100.dp))
            TextField(
                value = test1,
                onValueChange = { test1 = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
        )
        /*horizontalArrangement = Arrangement.Center)*/
        {
            Text(text = "test2", modifier = Modifier.width(100.dp))
            TextField(
                value = test2,
                onValueChange = { test2 = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }
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
        .padding(8.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Card(shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ){
            Image(painter = painter, contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }
}