package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.polimi.dima.uniquizapp.composables.Login
import com.polimi.dima.uniquizapp.composables.SignUpPage
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                //LoginApplication()
                SignUp()
            }
        }
    }
}

    @Composable
    fun LoginApplication(){
        val navController = rememberNavController()

        /*NavHost(navController = navController, startDestination = "login_page", builder = {
            composable("login_page", content = { Login(navController = navController) })
            composable("register_page", content = { SignUp(navController = navController) })
        })*/
    }
//}

@Preview(showBackground = true)
@Composable
fun SignUp(){
    val navController = rememberNavController()

    //SignUpPage()
    Login(navController = navController)
}
