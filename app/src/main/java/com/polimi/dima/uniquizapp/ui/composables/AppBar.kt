package com.polimi.dima.uniquizapp.ui.composables

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.GoogleSignInActivity
import com.polimi.dima.uniquizapp.MainActivity
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController, seeProfile: Boolean, seeBackArrow: Boolean, sharedViewModel: SharedViewModel, seeLogout : Boolean){

    var showAlert by remember { mutableStateOf(false) }
    if(showAlert){
        alertDialogLogout(navController)
    }

    CenterAlignedTopAppBar(
    title = { Text("UniQuizApp",
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        color = Color.White,
        modifier = Modifier.wrapContentSize(Alignment.Center))},
    modifier = Modifier.background(customizedBlue),
    navigationIcon = {
        if(seeBackArrow)
            IconButton(onClick = {
                var route = navController.previousBackStackEntry?.destination!!.route!!
                if( route == Screen.SubjectScreen.route)
                    route = route.replace("{subjectId}", sharedViewModel.subject!!.id)
                navController.navigate(route){
                    popUpTo(route){
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        },
    actions = {
        if(seeProfile)
            IconButton(onClick = { navController.navigate(Screen.Profile.route)}) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.Person,
                    contentDescription = ""
                )
            }
        else if(seeLogout){
            IconButton(
                onClick = {
                    showAlert = true
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(0.dp),
                content = {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = "Logout Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.Transparent, CircleShape)
                            .padding(4.dp)
                    )
                }
            )
        }
    },
    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = customizedBlue)
    )
}


@Composable
fun alertDialogLogout(navController: NavController){

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Logout", color = Color.Black)},
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


