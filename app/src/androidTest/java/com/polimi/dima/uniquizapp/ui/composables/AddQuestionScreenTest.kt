package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polimi.dima.uniquizapp.ui.viewModels.MockSharedViewModel
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class AddQuestionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    val navController: NavController = Mockito.mock(NavController::class.java)

    val sharedViewModel: SharedViewModel = MockSharedViewModel()

    @Before
    fun setup() {
        composeTestRule.setContent {
            AddQuestionScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }


    //  Test if Add a question Text is displayed
    @Test
    fun testAddQuestionTextIsDisplayed() {
        composeTestRule.onNodeWithText("Add a question").assertExists()
    }

    //  Test if Quiz drop down field is displayed
    @Test
    fun testQuizDropDownFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Quiz").assertExists()
    }

}
