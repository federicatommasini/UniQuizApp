package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue

@Composable
fun FloatingActionButtons(navController: NavController){

    var selected by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        if (selected) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (selected2) {
                    Text(
                        "To start a quiz click on the argument's name!",
                        color = Color.White,
                        fontWeight =  FontWeight.Bold ,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(color = customizedBlue, shape = RoundedCornerShape(10.dp))
                            .padding(3.dp)
                            .weight(0.8f)
                    )
                    Spacer(modifier = Modifier.weight(0.05f))
                } else {
                    Spacer(modifier = Modifier.weight(0.8f))
                }
                FloatingActionButton(
                    onClick = { selected2 = !selected2 },
                    backgroundColor = customizedBlue,
                    contentColor = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(Icons.Filled.Info, "")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(0.8f))
            }
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(route = "addQuestionScreen"){
                    popUpTo(Screen.AddQuestionScreen.route){
                        inclusive = true
                    }}
                },
                backgroundColor = customizedBlue,
                contentColor = Color.White,
                modifier = Modifier.padding(vertical = 8.dp),
                icon = { Icon(Icons.Filled.Edit, "", modifier = Modifier.align(Alignment.End))},
                text = { Text("Add a quiz",  modifier = Modifier.align(Alignment.Start)) }

            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                onClick = { selected = !selected },
                backgroundColor = customizedBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    }
}


