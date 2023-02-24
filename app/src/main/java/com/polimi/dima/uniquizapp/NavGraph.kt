package com.polimi.dima.uniquizapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.polimi.dima.uniquizapp.composables.Login
import com.polimi.dima.uniquizapp.composables.SignUp

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        //the following lines map the route to the screen object
        composable(route = Screen.Login.route) {
            Login(//navController = navController)
            )
        }
        /*composable(route = Screen.SignUp.route) {
            SignUp(navController = navController)
        }*/
    }
}