package com.polimi.dima.uniquizapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.polimi.dima.uniquizapp.ui.composables.Calendar
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {
    /*companion object {
        private var instance: MainActivity? = null

        fun getInstance(): MainActivity? {
            return instance
        }
    }*/

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

    companion object {
        @JvmStatic
    fun getGoogleLoginAuth(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("71784389950-kf3b9kitih2po323491429ai8oc1rvpk.apps.googleusercontent.com")
            .requestId()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, gso)
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