package com.polimi.dima.uniquizapp.composables

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground

@Composable
fun Profile(navController: NavController){

    var notification = rememberSaveable{ mutableStateOf("") }

    if(notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    //var imageUri by remember{ mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    /*val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        uri: Uri? -> imageUri = uri
    }*/
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(customizedBlue),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){
            Text(text = "Cancel", modifier = Modifier.clickable {
                notification.value = "Cancelled"
            })
            Text(text = "Save", modifier = Modifier.clickable {
                notification.value = "Profile updated"
            })
            Text(text = "Profile",
                fontSize = 32.sp,
                color = whiteBackground,
                style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            )
        }
        /*Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            imageUri?.let{
                if(Build.VERSION.SDK_INT < 28){
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                bitmap.value?.let{ btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .padding(20.dp))
                }
            }*/
        ProfileImage()
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            Text(text = "Name")
        }
            Spacer(modifier = Modifier.height(12.dp))}
            /*Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { launcher.launch("image/*") }) {
                    Text(text = "Pick Image")
                }
            }*/
            Divider()
        }*/

@Composable
fun ProfileImage(){
    val imageUri = rememberSaveable { mutableStateOf("") }
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
                    .clickable { },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }

}
