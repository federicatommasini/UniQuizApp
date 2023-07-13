package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.ui.theme.customGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Groups(navController: NavController, sharedViewModel: SharedViewModel){
    val subjects = sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val pagerState = rememberPagerState()
    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false,null)},
        bottomBar = { BottomNavigationBar(navController = navController) },
        modifier = Modifier.fillMaxHeight(1f)
    ) { padding ->
        Column(modifier = Modifier.fillMaxHeight(1f)){
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxHeight(0.1f)
            ){
                ScreenTitle("Ranking")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Slider(subjects, pagerState, sharedViewModel, navController)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight(0.1f).fillMaxWidth()) {
                Spacer(modifier = Modifier.size(5.dp))
                DotsIndicator(
                    totalDots = subjects.size,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = customizedBlue,
                    unSelectedColor = customGray
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
            if(subjects.isEmpty()){
                Text("Add some subjects to get started! You will be able to confront your results with the other users.",
                    fontWeight = FontWeight.Bold,
                    color = customizedBlue,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(30.dp).border(1.5.dp, customizedBlue, RoundedCornerShape(10.dp)),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}