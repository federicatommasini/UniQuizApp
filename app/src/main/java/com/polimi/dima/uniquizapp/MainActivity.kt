package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.polimi.dima.uniquizapp.composables.Profile
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                navController = rememberNavController()
                //SetupNavGraph(navController = navController)
                test(navController = navController)

            }
        }
    }
}

@Composable
fun test(navController: NavController){
    Profile(navController)
}