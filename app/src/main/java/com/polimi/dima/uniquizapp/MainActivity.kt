package com.polimi.dima.uniquizapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.polimi.dima.uniquizapp.ui.composables.Calendar
import com.polimi.dima.uniquizapp.ui.composables.Groups
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    lateinit var navController: NavHostController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
                DefaultPreview()
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
        Groups(navController, sharedViewModel)
    }
}