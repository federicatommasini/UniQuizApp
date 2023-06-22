package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun SubjectBar(subjectId: String?, sharedViewModel: SharedViewModel){

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    val subject = sharedViewModel.subjectViewModel.getSubjectById(subjectId!!)
    var present : Boolean = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(customizedBlue)
            .align(Alignment.CenterHorizontally)
            .padding(end = 20.dp, start = 20.dp)
        ) {
            Text(
                text = subject!!.name,
                fontWeight = FontWeight.Bold,
                color = customLightGray,
                modifier = Modifier.weight(0.8f).padding(top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Start,
                fontSize = 30.sp
            )
            for( s in userSubjectState)
                if(s.equals(subject))
                    present = true;
            /*OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = customLightGray)
            ) {}*/
            if(!present){
               Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = customLightGray),
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically),
                    onClick = {val user = sharedViewModel.userViewModel.addSubjectToUser(subject!!,sharedViewModel.user!!.id)
                        sharedViewModel.addUser(user)},
                    content = {
                        Text(
                            text = "Add",
                            fontWeight = FontWeight.Bold ,
                            color = customizedBlue
                        )
                    }
                )
            }else {
                Spacer(modifier = Modifier
                    .weight(0.2f)
                    .padding(end = 10.dp))
            }

        }
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf("Arguments", "Documents")
        TabRow(
            selectedTabIndex = selectedItem,
            contentColor = customizedBlue,
            backgroundColor = customLightGray
        ) {
            items.forEachIndexed { index, item ->
                Tab(
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    text = { Text(
                        text = item,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black) }

                )
            }
        }
    }

}