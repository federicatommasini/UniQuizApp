package com.polimi.dima.uniquizapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.polimi.dima.uniquizapp.ui.composables.Calendar
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    lateinit var navController: NavHostController
    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var navController: NavHostController
    UniQuizAppTheme {
        val sharedViewModel: SharedViewModel = viewModel()

        navController = rememberNavController()
        SetupNavGraph(navController = navController)
        Calendar(navController, sharedViewModel)
    }
}