package com.polimi.dima.uniquizapp

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.polimi.dima.uniquizapp.ui.composables.Login
import com.polimi.dima.uniquizapp.ui.composables.Profile
import com.polimi.dima.uniquizapp.ui.composables.SignUp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.polimi.dima.uniquizapp.ui.composables.Calendar
import com.polimi.dima.uniquizapp.ui.composables.Groups
import com.polimi.dima.uniquizapp.ui.composables.Home
import com.polimi.dima.uniquizapp.ui.composables.Pdf
import com.polimi.dima.uniquizapp.ui.composables.SubjectScreen
import com.polimi.dima.uniquizapp.ui.composables.Subjects
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun SetupNavGraph(navController: NavHostController){

    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        //the following lines map the route to the screen object
        composable(route = Screen.Login.route) {
            Login(navController = navController, sharedViewModel)
        }
        composable(route = Screen.SignUp.route) {
            SignUp(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            Profile(navController = navController, sharedViewModel)
        }
        composable(BottomNavItem.Home.screen_route){
            /*LaunchedEffect(key1 = it){
                val result = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
                if (result != null) {
                    Log.d("home scree", "${result.firstName}")
                }
            }*/
            Home(navController = navController, sharedViewModel)
        }
        composable(BottomNavItem.Subjects.screen_route) {
            Subjects(navController = navController, sharedViewModel)
        }
        composable(BottomNavItem.Groups.screen_route) {
            Groups(navController = navController, sharedViewModel)
        }
        composable(BottomNavItem.Calendar.screen_route) {
            Calendar(navController = navController, sharedViewModel)
        }
        composable(Screen.SubjectScreen.route, arguments = listOf(navArgument("subjectId"){
            type = NavType.StringType
        })) {
            SubjectScreen(navController = navController,it.arguments?.getString("subjectId"), sharedViewModel)
        }
        composable(Screen.Pdf.route){
            Pdf(navController = navController, sharedViewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Subjects,
        BottomNavItem.Groups,
        BottomNavItem.Calendar
    )
    BottomNavigation(
        backgroundColor = customizedBlue,
        contentColor = Color.White
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {  Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
