package com.polimi.dima.uniquizapp.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.BottomNavItem
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.*


@Composable
fun Home(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Welcome to the UniQuiz app!",
                fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(30.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp)
            Text(text = "With this app you can schedule your study for your university exams, learning using simple and fun quizzes!",
                    fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(30.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
            /*Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                   modifier = Modifier.size(300.dp).align(Alignment.CenterHorizontally).padding(10.dp,0.dp)
            )*/
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ){

               /* Box(

                ) {
                    Image(painter = painterResource(id = R.drawable.books),
                        contentDescription = "books",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.clickable { //navController.navigate(route = BottomNavItem.Subjects.screen_route)
                        }
                            .fillMaxWidth()
                            .padding(30.dp, 10.dp)
                            .border(
                                BorderStroke(2.dp, customizedBlue),
                                RoundedCornerShape(500.dp)
                            )
                            .clip(RoundedCornerShape(500.dp))
                    )
                    Text(text = "Your Subjects",
                        fontWeight = FontWeight.Bold,
                        color = customizedBlue,
                        modifier = Modifier.align(Alignment.CenterStart).padding(50.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
                Image(painter = painterResource(id = R.drawable.groups),
                    contentDescription = "groups",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.clickable { //navController.navigate(route = BottomNavItem.Subjects.screen_route)
                         }
                        .fillMaxWidth()
                        .padding(30.dp,10.dp)
                        .border(
                            BorderStroke(2.dp, customizedBlue),
                            RoundedCornerShape(500.dp)
                        )
                        .clip(RoundedCornerShape(500.dp))
                )*/
            }
        }
    }

}

@Composable
fun Subjects(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Subjects Screen",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun Groups(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Groups Screen",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun Calendar(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Calendar Screen",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}