package com.polimi.dima.uniquizapp

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.security.AccessController.getContext
import javax.security.auth.Subject

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {
    /*companion object {
        private var instance: MainActivity? = null

        fun getInstance(): MainActivity? {
            return instance
        }
    }*/

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var navController: NavHostController
    UniQuizAppTheme {
        navController = rememberNavController()
        SetupNavGraph(navController = navController)
        //Subjects(navController)
    }
}