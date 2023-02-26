package com.polimi.dima.uniquizapp

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.polimi.dima.uniquizapp.composables.*
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        //the following lines map the route to the screen object
        composable(route = Screen.Login.route) {
            Login(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUp(navController = navController)
        }
       composable(BottomNavItem.Home.screen_route) {
            Home(navController = navController)
        }
        composable(BottomNavItem.Subjects.screen_route) {
            Subjects(navController = navController)
        }
        composable(BottomNavItem.Groups.screen_route) {
            Groups(navController = navController)
        }

    }
}

/*@Composable
fun BottomBarMain(navController : NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            Home()
        }
        composable(BottomNavItem.Subjects.screen_route) {
            Subjects()
        }
        composable(BottomNavItem.Groups.screen_route) {
            Groups()
        }
    }
}*/

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Subjects,
        BottomNavItem.Groups
    )
    BottomNavigation(
        backgroundColor = customizedBlue,
        contentColor = Color.Black
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {  Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
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
