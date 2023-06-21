package com.polimi.dima.uniquizapp.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.grayBackground
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel

@Composable
fun SubjectBar(subjectId: String?, sharedViewModel: SharedViewModel){

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    val subject = sharedViewModel.subjectViewModel.getSubjectById(subjectId!!)
    var present : Boolean = false

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(customizedBlue)) {
        Text(
            text = subject!!.name,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(0.8f).padding(8.dp),
            textAlign = TextAlign.Start,
            fontSize = 30.sp
        )
        for( s in userSubjectState)
            if(s.equals(subject))
                present = true;
        if(!present){
                // Inner content including an icon and a text label
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Favorite",
                    tint = customizedBlue,
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(end = 10.dp)
                        .shadow(elevation = 5.dp, shape = CircleShape)
                        .clickable{
                            val user = sharedViewModel.userViewModel.addSubjectToUser(subject!!,sharedViewModel.user!!.id)
                            sharedViewModel.addUser(user)
                        }
                        .align(Alignment.CenterVertically)
                        .background(customLightGray, shape = CircleShape)

                )
        }else {
            Spacer(modifier = Modifier
                .weight(0.2f)
                .padding(end = 10.dp))
        }
    }
}