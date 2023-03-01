package com.polimi.dima.uniquizapp

sealed class Screen(val route: String){
    object Login: Screen(route = "login_screen")
    object SignUp: Screen(route = "signup_screen")
    object Profile: Screen(route = "profile_screen")
}
