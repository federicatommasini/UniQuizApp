package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polimi.dima.uniquizapp.composables.Profile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {
    //private lateinit var viewModel: UserViewModel


    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(androidx.appcompat.R.layout.abc_activity_chooser_view)
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
    UniQuizAppTheme {
        //Home()
    }
}