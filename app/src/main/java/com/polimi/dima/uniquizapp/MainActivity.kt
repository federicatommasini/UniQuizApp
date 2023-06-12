package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.polimi.dima.uniquizapp.ui.composables.Profile
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(androidx.appcompat.R.layout.abc_activity_chooser_view)
        setContent {
            UniQuizAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
                //test(navController = navController)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UniQuizAppTheme {
        //Home()
    }
}

@Composable
fun test(navController: NavController){
    Profile(navController)
}