package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.polimi.dima.uniquizapp.BottomNavItem
import com.polimi.dima.uniquizapp.GoogleSignInActivity
import com.polimi.dima.uniquizapp.MainActivity
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.runBlocking

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Profile(navController: NavController, sharedViewModel: SharedViewModel) {

    val uniViewModel = sharedViewModel.uniViewModel
    var user = sharedViewModel.user
    Log.d("USER", user.toString())
    val universityFromUser = runBlocking { uniViewModel.getUniById(user!!.universityId) }

    /*val notification = rememberSaveable { mutableStateOf("") }

    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }*/

    var showAlert by remember {mutableStateOf(false)}

    if(showAlert){
        alertDialogLogout(navController)
    }

    var isEditable by remember { mutableStateOf(false) }

    var firstName by rememberSaveable { mutableStateOf(user!!.firstName) }
    var lastName by rememberSaveable { mutableStateOf(user!!.lastName) }
    var username by rememberSaveable { mutableStateOf(user!!.username) }
    var university by rememberSaveable { mutableStateOf(universityFromUser!!.name) }
    var password by rememberSaveable { mutableStateOf(user!!.password) }
    var email by rememberSaveable { mutableStateOf(user!!.email) }

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(290.dp)
                .clip(shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                .padding(0.dp)
                .background(customizedBlue)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                //horizontalArrangement = Arrangement.Center
            )
            {
                Box(modifier = Modifier.fillMaxWidth()){
                    IconButton(
                        onClick = {
                            //navController.popBackStack()  //figure out why this was needed or not
                            navController.navigate(BottomNavItem.Home.screen_route){
                                popUpTo(BottomNavItem.Home.screen_route) //i dont know if this is correct
                                {
                                    inclusive = true
                                }
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.dp)
                            .align(Alignment.TopStart),
                        content = {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back Icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(44.dp)
                                    .align(Alignment.TopEnd) //doesnt seem to work
                                    .background(Color.Transparent, CircleShape)
                                    .padding(4.dp)
                            )
                        }
                    )
                    Text(
                        text = "Profile",
                        fontSize = 32.sp,
                        modifier = Modifier.align(Alignment.Center),
                        color = whiteBackground,
                        style = androidx.compose.ui.text.TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                    IconButton(
                        onClick = {
                            showAlert = true
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.dp)
                            .align(Alignment.TopEnd),
                        content = {
                            Icon(
                                Icons.Default.Logout,
                                contentDescription = "Logout Icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(44.dp)
                                    .align(Alignment.TopEnd)
                                    .background(Color.Transparent, CircleShape)
                                    .padding(4.dp)
                            )
                        }
                    )
                }
            }
            ProfileImage(user)
        }
        CustomSpacer()
        ProfileTextField(field = firstName, nameField = "First Name", colors = customizedColors)
        CustomSpacer()
        ProfileTextField(field = lastName, nameField = "Last Name", colors = customizedColors)
        CustomSpacer()
        ProfileTextField(field = username, nameField = "Username", colors = customizedColors)
        CustomSpacer()
        ProfileTextField(field = university, nameField = "University", colors = customizedColors)
        CustomSpacer()
        ProfileTextField(field = email, nameField = "Email", colors = customizedColors)
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
                onValueChange = { password = it
                                Log.d("IT", it)},
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
                                passwordVisibility.value = false
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility),
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                passwordVisibility.value = true
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility_off),
                                contentDescription = null
                            )
                        }
                    }
                },
                enabled = isEditable
            )
        }
        CustomSpacer()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(
                onClick = {
                    if(isEditable){
                        //var passwordWrapper = StringWrapper(password)
                        runBlocking { user = sharedViewModel.userViewModel.updateProfile(password, user!!.id) }
                        user?.let { sharedViewModel.addUser(it) }
                    }
                    isEditable = !isEditable
                    Log.d("PASS", password)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                shape = RoundedCornerShape(20.dp),
                content = {
                    Text(text = if (isEditable) "Save" else "Edit", color = whiteBackground)
                    // Use the isEditable state variable to control the enabled/disabled state of the fields
                }
            )
        }
    }
}



@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileImage(user: User?) {

    val imageUri = rememberSaveable { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? -> uri?.let { imageUri.value = it.toString() }
    }

    val profilePic : String = user!!.profilePicUrl
    //Log.d("user", user.toString())

    //Log.d("PIC", profilePic.toString())

    var showDialog by remember { mutableStateOf(false) }

    val test = "https://uvejzsepcmqpowatjgyy.supabase.co/storage/v1/object/sign/profile%20images/IMG20221015184902.jpg?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJwcm9maWxlIGltYWdlcy9JTUcyMDIyMTAxNTE4NDkwMi5qcGciLCJpYXQiOjE2ODgwNTAyNDAsImV4cCI6MTcxOTU4NjI0MH0.cng7K60K58OUYkbrNRrXHDZRuCv3bNT8hngMI3eYMHI&t=2023-06-29T14%3A50%3A40.824Z"
    val painter = rememberImagePainter(
        if(imageUri.value.isEmpty()){
            //R.drawable.ic_user
            profilePic
        }
        else{
            imageUri.value
            Log.d("IMAGE", imageUri.value)
        }
    )

    Column(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.padding(30.dp))
        Box(modifier = Modifier
            .size(140.dp, 140.dp)){
            Card(shape = CircleShape,
                modifier = Modifier
                    .padding(0.dp)
                    .size(140.dp)
                    .align(Alignment.Center),
            ) {
                Image(
                    painter = painter, contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { showDialog = true },
                    contentScale = ContentScale.Crop
                )
                if(showDialog) {
                    Dialog(onDismissRequest = { showDialog = false },
                        content = {
                            FullImage(
                                imageResource = painter,
                                onDismiss = { showDialog = false }
                            )
                        }
                    )
                }
            }
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text(text = user!!.username, fontSize = 26.sp,
                color = whiteBackground,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp)
            )
        }
    }
}

@Composable
fun FullImage(
    imageResource : Painter,
    onDismiss: () -> Unit)
{
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent, RectangleShape)){
        Image(
            painter = imageResource,
            contentDescription = "Full-screen Image",
            modifier = Modifier
                .fillMaxSize()
                .size(16.dp),
            contentScale = ContentScale.Fit
        )
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(24.dp))
        ){
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ProfileTextField(field: String, nameField: String, colors: TextFieldColors){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(text = nameField, modifier = Modifier.width(100.dp))
        TextField(
            value = field,
            onValueChange = {},
            colors = colors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(grayBackground, RoundedCornerShape(20.dp)),
            enabled = false
        )
    }
}

@Composable
fun alertDialogLogout(navController: NavController){

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "AlertDialog", color = Color.Black)},
            text = {Text(text = "Are you sure you want to log out?", color = Color.Black)},
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val activity = context as MainActivity
                        val signInGoogle = GoogleSignInActivity()
                        signInGoogle.initialize(activity)
                        signInGoogle.googleSignInClient.signOut()

                        navController.navigate(route = Screen.Login.route){
                            popUpTo(route = Screen.Login.route) //i dont know if this is correct
                            {
                                inclusive = true
                            }
                        }
                    }) {
                    Text(text = "Confirm", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(text = "Cancel", color = Color.Black)
                }
            },
            backgroundColor = grayBackground,
            shape = RoundedCornerShape(20.dp)
        )
    }
}



