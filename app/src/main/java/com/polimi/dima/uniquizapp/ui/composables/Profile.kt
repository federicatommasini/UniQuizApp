package com.polimi.dima.uniquizapp.ui.composables

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.data.Constants.BUCKET_NAME
import com.polimi.dima.uniquizapp.data.Constants.SUPABASE_KEY
import com.polimi.dima.uniquizapp.data.Constants.SUPABASE_URL
import com.polimi.dima.uniquizapp.data.model.UriPathFinder
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.model.UserExam
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Profile(navController: NavController, sharedViewModel: SharedViewModel) {

    var user = sharedViewModel.user
    var showCamera : Boolean = true
    if(user!!.profilePicUrl != ""){
        showCamera = false
    }

    var showAlert = remember { mutableStateOf(false) }
    if(showAlert.value){
        PopUpCancel(
            title = "Logout",
            text = "Are you sure you want to log out?",
            isPopupVisible = showAlert ,
            isQuizScreen = false,
            sharedViewModel = sharedViewModel,
            navController = navController
        )
    }
    val configuration = LocalConfiguration.current

    Scaffold(
        topBar = {AppBar(navController = navController,false, true, sharedViewModel, true,showAlert)}
    ) { padding ->
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row( modifier = Modifier
                    .fillMaxSize()
                    .background(whiteBackground)
                    .padding(padding)
                ){
                    Box(
                        modifier = Modifier.weight(0.5f)
                            .clip(shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                            .padding(0.dp)
                            .background(customizedBlue)
                    ){
                        ProfileImage(user, sharedViewModel, showCamera, 0.5f)
                    }
                    Column(modifier = Modifier.weight(0.5f).fillMaxHeight().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center) {
                        FieldsProfileSection(sharedViewModel)
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(whiteBackground)
                        .padding(padding),
                    verticalArrangement = Arrangement.Top
                ) {
                    Column(
                        modifier = Modifier.weight(0.3f).fillMaxWidth().verticalScroll(rememberScrollState())
                            .clip(shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                            .background(customizedBlue)
                    ) {
                        ProfileImage(user, sharedViewModel, showCamera,0.4f)
                    }
                    Column(modifier = Modifier.weight(0.6f).fillMaxWidth().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center) {
                        FieldsProfileSection(sharedViewModel)
                    }
                }
            }
        }

    }

}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalCoilApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ProfileImage(user: User?, sharedViewModel: SharedViewModel, showCamera: Boolean, fraction: Float ) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { imageUri = it })

    val clicked = remember { mutableStateOf(false) }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status == PermissionStatus.Granted && clicked.value) {
            filePickerLauncher.launch("*/*")
        }
    }

    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val uriPathFinder = UriPathFinder()
    val profilePic : String = user!!.profilePicUrl
    var filePath : String? = ""

    val painter = rememberImagePainter(
        if( profilePic != "" ){ profilePic }
        else if( imageUri == null ){ R.drawable.ic_user }
        else{ imageUri }
    )

    var showDialog by remember { mutableStateOf(false) }
    var onlyOnce by remember { mutableStateOf(true) }


    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(modifier = Modifier.fillMaxWidth(fraction).aspectRatio(1f).padding(10.dp)) {
            Card(
                shape = CircleShape,
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center),
            ) {
                if (imageUri != null) {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, imageUri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }
                    if (bitmap.value != null) {
                        Image(
                            bitmap = bitmap.value!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                                .clickable {
                                    showDialog = true
                                },
                            contentScale = ContentScale.Crop
                        )
                        runBlocking {
                            filePath = uriPathFinder.getPath(context, imageUri!!)
                        }
                        val fileName = getNameImage(imageUri!!, context)
                        if (onlyOnce) {
                            uploadImage(filePath!!, sharedViewModel, fileName!!)
                            onlyOnce = false
                        }
                    }
                } else {
                    Image(
                        painter = painter, contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                            .clickable { showDialog = true },
                        contentScale = ContentScale.Crop
                    )
                }
                if (showDialog) {
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
            if (showCamera) {
            IconButton(
                onClick = {
                    clicked.value = true
                    if (permissionState.status.isGranted) {
                        filePickerLauncher.launch("*/*")
                    }
                    else{
                        permissionState.launchPermissionRequest()
                    }},
                modifier = Modifier
                    .fillMaxSize(0.2f)
                    .padding(0.dp)
                    .align(Alignment.BottomEnd),
                content = {
                    Icon(
                        Icons.Default.PhotoCamera,
                        contentDescription = "Edit Icon",
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                            .background(Color.White, CircleShape)
                            .padding(4.dp)
                    )
                }
            )
            }
        }
        Row(modifier = Modifier
            .wrapContentHeight()
            .padding(10.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text(text = user!!.username, fontSize = 28.sp,
                color = whiteBackground,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp)
            )
        }
    }

}
@Composable
fun FieldsProfileSection(sharedViewModel: SharedViewModel){

    var user = sharedViewModel.user
    val uniViewModel = sharedViewModel.uniViewModel
    val universityFromUser = runBlocking { uniViewModel.getUniById(user!!.universityId) }
    var isEditable by remember { mutableStateOf(false) }

    var firstName by rememberSaveable { mutableStateOf(user!!.firstName) }
    var lastName by rememberSaveable { mutableStateOf(user!!.lastName) }
    var username by rememberSaveable { mutableStateOf(user!!.username) }
    var university by rememberSaveable { mutableStateOf(universityFromUser!!.name) }
    var password by rememberSaveable { mutableStateOf(user!!.password) }
    var email by rememberSaveable { mutableStateOf(user!!.email) }
    val passwordVisibility = remember { mutableStateOf(false) }

    val customizedColors = TextFieldDefaults.textFieldColors(
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent)
    CustomSpacer()
    ProfileTextField(field = firstName, nameField = "First Name", colors = customizedColors)
    CustomSpacer()
    ProfileTextField(field = lastName, nameField = "Last Name", colors = customizedColors)
    CustomSpacer()
    ProfileTextField(field = username, nameField = "Username", colors = customizedColors)
    CustomSpacer()
    ProfileTextField(
        field = university,
        nameField = "University",
        colors = customizedColors
    )
    CustomSpacer()
    ProfileTextField(field = email, nameField = "Email", colors = customizedColors)
    CustomSpacer()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(text = "Password",textAlign = TextAlign.Center, modifier = Modifier.weight(0.3f))
        TextField(
            value = password,
            onValueChange = { password = it },
            colors = customizedColors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.weight(0.7f)
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
                        onClick = { passwordVisibility.value = true },
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
                if (isEditable) {

                        user = runBlocking {sharedViewModel.userViewModel.updateProfile(
                            password,
                            user!!.id
                        )}

                    sharedViewModel.addUser(user!!)
                }
                isEditable = !isEditable
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
            shape = RoundedCornerShape(20.dp),
            content = {
                Text(text = if (isEditable) "Save" else "Edit", color = whiteBackground)
            }
        )
    }
}

@Composable
fun FullImage(
    imageResource : Painter,
    onDismiss: () -> Unit
){
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
        modifier = Modifier.fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(text = nameField, textAlign = TextAlign.Center,modifier = Modifier.weight(0.3f))
        TextField(
            value = field,
            onValueChange = {},
            colors = colors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.weight(0.7f)
                .background(grayBackground, RoundedCornerShape(20.dp)),
            enabled = false
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun uploadImage(imagePath : String, sharedViewModel: SharedViewModel, fileName: String){

    val supabaseUrl = SUPABASE_URL
    val supabaseKey = SUPABASE_KEY
    val bucketName = BUCKET_NAME

    val file = File(imagePath)
    val byteArray = file.readBytes()

    val client = createSupabaseClient(supabaseUrl, supabaseKey) {
        install(Storage)
        install(GoTrue)
    }
    val lifecycleCoroutineScope = rememberCoroutineScope()
    lifecycleCoroutineScope.launch(Dispatchers.IO) {
        runBlocking { uploadToSupabase(client, fileName, byteArray, bucketName, sharedViewModel) }

    }
}

suspend fun uploadToSupabase(client : SupabaseClient, fileName: String, byteArray: ByteArray, bucketName: String, sharedViewModel: SharedViewModel) {
    runBlocking { client.storage[bucketName].upload(fileName, byteArray, false) }
    val url = client.storage[bucketName].publicUrl(fileName)
    runBlocking { saveItToDb(sharedViewModel, url) }
}

fun saveItToDb(sharedViewModel: SharedViewModel, url : String){
    var oldUser = sharedViewModel.user
    var justUpdatedUser = runBlocking {sharedViewModel.userViewModel.uploadProfileIcon(oldUser!!.id, url)}
    sharedViewModel.addUser(justUpdatedUser)
}

private fun getNameImage(contentURI: Uri, context: Context) : String? {
    var thePath = "no-path-found"
    val filePathColumn = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
    val cursor: Cursor? = context.contentResolver.query(contentURI, filePathColumn, null, null, null)
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            thePath = columnIndex?.let { cursor.getString(it) }.toString()
        }
    }
    cursor?.close()
    return thePath
}

