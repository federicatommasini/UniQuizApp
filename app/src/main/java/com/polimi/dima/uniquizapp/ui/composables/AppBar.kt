package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController, seeProfile: Boolean, seeBackArrow: Boolean, sharedViewModel: SharedViewModel, seeLogout : Boolean, isVisible: MutableState<Boolean>?){

    CenterAlignedTopAppBar(
        title = {
            Text("UniQuiz",
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color.White,
                modifier = Modifier.wrapContentSize(Alignment.Center))
                },
        modifier = Modifier.background(customizedBlue),
        navigationIcon = {
            if(seeBackArrow )
                IconButton(onClick = {
                    var route = navController.previousBackStackEntry?.destination!!.route!!
                    var finalRoute =route
                    if( route == Screen.SubjectScreen.route)
                        finalRoute = route.replace("{subjectId}", sharedViewModel.subject!!.id)
                    if(!seeLogout && null!=isVisible)
                        isVisible.value=true
                    else{
                        navController.navigate(finalRoute){
                            popUpTo(route){
                                inclusive = true
                            }
                        }
                    }
                })
                {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = ""
                    )
                }
        },
        actions = {
            if(seeProfile)
                IconButton(onClick = { navController.navigate(Screen.Profile.route)}) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.Person,
                        contentDescription = ""
                    )
                }
            else if(seeLogout){
                IconButton(
                    onClick = {
                        isVisible!!.value = true
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(0.dp),
                    content = {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color.Transparent, CircleShape)
                                .padding(4.dp)
                        )
                    }
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = customizedBlue)
    )
}


