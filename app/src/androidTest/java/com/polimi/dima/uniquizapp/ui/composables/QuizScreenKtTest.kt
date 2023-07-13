package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polimi.dima.uniquizapp.ui.viewModels.MockSharedViewModel
import androidx.compose.material.OutlinedButton
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.w3c.dom.Text

@RunWith(AndroidJUnit4::class)
class QuizTest() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    val navController: NavController = Mockito.mock(NavController::class.java)

    val sharedViewModel: SharedViewModel = MockSharedViewModel()

    @Test
    fun quizScreenTest() {
        composeTestRule.setContent {

            QuizScreen(navController = navController, quizId = "1", questionId = 0, sharedViewModel = sharedViewModel)
        }

        composeTestRule.onNodeWithText("Question 1/1").assertExists()

    }
    @Test
    fun quizScreenQuestionContentDisplayed() {
        composeTestRule.setContent {
            QuizScreen(navController = navController, quizId = "1", questionId = 0, sharedViewModel = sharedViewModel)
        }

        composeTestRule.onNodeWithText("questionText").assertExists()
    }

    @Test
    fun quizScreenCheckButtonDisplayed() {
        composeTestRule.setContent {
            QuizScreen(navController = navController, quizId = "1", questionId = 0, sharedViewModel = sharedViewModel)
        }

        composeTestRule.onNodeWithText("Check").assertExists()
    }

    @Test
    fun quizScreenNextButtonNotDisplayedBeforeCheck() {
        composeTestRule.setContent {
            QuizScreen(navController = navController, quizId = "1", questionId = 0, sharedViewModel = sharedViewModel)
        }
        composeTestRule.onNodeWithText("Next").assertDoesNotExist()
    }


    @Test
    fun quizScreenAnswerOptionsDisplayed() {
        composeTestRule.setContent {
            QuizScreen(navController = navController, quizId = "1", questionId = 0, sharedViewModel = sharedViewModel)
        }

        composeTestRule.onNodeWithText("text1").assertExists()
        composeTestRule.onNodeWithText("text2").assertExists()
        composeTestRule.onNodeWithText("text3").assertExists()
        composeTestRule.onNodeWithText("text4").assertExists()

    }


}




