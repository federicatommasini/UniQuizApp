package com.polimi.dima.uniquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.polimi.dima.uniquizapp.composables.SignUpPage
import com.polimi.dima.uniquizapp.ui.theme.UniQuizAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniQuizAppTheme {
                SignUp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUp(){
    SignUpPage()
}
