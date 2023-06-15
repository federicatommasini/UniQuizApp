package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.polimi.dima.uniquizapp.ui.composables.SubjectScreen
import com.polimi.dima.uniquizapp.ui.composables.Subjects
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.security.auth.Subject

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

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

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var navController: NavHostController
    UniQuizAppTheme {
        navController = rememberNavController()
        SetupNavGraph(navController = navController)
        Subjects(navController)
    }
}