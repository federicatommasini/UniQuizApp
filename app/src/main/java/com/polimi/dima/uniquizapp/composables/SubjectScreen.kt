package com.polimi.dima.uniquizapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.theme.whiteBackground

@Composable
fun SubjectScreen(navController: NavController, subjectId: String?) {
    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(whiteBackground)
    ){
        Row(modifier = Modifier.align(Alignment.Start)){
            Text(
                text = subjectId.toString(),
                fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier
                    .padding(30.dp),
                textAlign = TextAlign.Start,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.size(100.dp))
            Button(
                onClick = { /* ... */ },
                shape = CircleShape,
                modifier = Modifier.padding(end = 30.dp).align(Alignment.CenterVertically)
            ) {
                // Inner content including an icon and a text label
                Icon(
                    imageVector = Icons.Default.Add, //TODO this should be a plus if the subject is not already in the user's list
                    contentDescription = "Favorite",
                    modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                )
            }
        }
    }
}
