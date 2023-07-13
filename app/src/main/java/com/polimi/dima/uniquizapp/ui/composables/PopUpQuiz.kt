package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.GoogleSignInActivity
import com.polimi.dima.uniquizapp.MainActivity
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun PopUpCancel(title: String,
          text:String,
          isPopupVisible: MutableState<Boolean>,
          isQuizScreen: Boolean,
          sharedViewModel: SharedViewModel,
          navController: NavController
){
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { isPopupVisible.value = false },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        dismissButton = {
            Button(
                onClick = {
                    isPopupVisible.value = false
                },
                shape = CircleShape
            ) {
                Text(text = "Cancel")
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    isPopupVisible.value = false
                    if(isQuizScreen){
                        var route = navController.previousBackStackEntry?.destination!!.route!!
                        var finalRoute =route
                        if( route == Screen.SubjectScreen.route)
                            finalRoute = route.replace("{subjectId}", sharedViewModel.subject!!.id)
                        navController.navigate(finalRoute){
                            popUpTo(route){
                                inclusive = true
                            }
                        }
                    }else{
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
                    }
                },
                shape = CircleShape
            ) {
                Text(text = "Confirm")
            }
        },

        properties = DialogProperties(usePlatformDefaultWidth = true),
        modifier = Modifier.background(Color.White, RoundedCornerShape(20.dp))
    )
}