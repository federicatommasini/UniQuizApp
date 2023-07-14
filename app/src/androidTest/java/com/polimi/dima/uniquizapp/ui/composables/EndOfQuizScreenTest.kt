package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polimi.dima.uniquizapp.data.model.Answer
import com.polimi.dima.uniquizapp.data.model.Question
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class EndOfQuizScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // A mock NavController is used to test navigation actions
    val navController = mock(NavController::class.java)

    // A SharedViewModel is used to manage shared state between composables
    val sharedViewModel = SharedViewModel()

    @Before
    fun setup() {
        sharedViewModel.points = 5
        sharedViewModel.quiz = Quiz("1","quiz1",listOf(Question("questionText",
            listOf(
                Answer("text1",true),
                Answer("text2",false),
                Answer("text3",false),
                Answer("text4",false)
            ),listOf())))
        sharedViewModel.subject = Subject("1","1234", "subject",listOf("1","2","3"), mapOf(Pair("1",1 as Integer)), "", listOf())
    }

    @Test
    fun testEndOfQuizScreen_BackToQuizzesButton() {
        composeTestRule.setContent {
            EndOfQuizScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composeTestRule.onNodeWithText("Go back to Quizzes").assertExists()
    }

    @Test
    fun testEndOfQuizScreen_GoToHomeButton() {
        composeTestRule.setContent {
            EndOfQuizScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composeTestRule.onNodeWithText("Go to Home").assertExists()
    }

}
