package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class FloatingActionButtonsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val navController = mock(NavController::class.java)

    @Test
    fun testFloatingActionButtons_DisplayMainButton() {
        composeTestRule.setContent {
            FloatingActionButtons(navController = navController)
        }
        composeTestRule.onNodeWithContentDescription("Plus").assertExists()
    }

    @Test
    fun testFloatingActionButtons_MainButtonClicked() {
        composeTestRule.setContent {
            FloatingActionButtons(navController = navController)
        }

        composeTestRule.onNodeWithContentDescription("Plus").performClick()

        composeTestRule.onNodeWithText("Add a quiz").assertExists()
        composeTestRule.onNodeWithContentDescription("Info").assertExists()

        composeTestRule.onNodeWithContentDescription("Edit").assertExists()
    }

    @Test
    fun testFloatingActionButtons_InfoButtonClicked() {
        composeTestRule.setContent {
            FloatingActionButtons(navController = navController)
        }

        composeTestRule.onNodeWithContentDescription("Plus").performClick()
        // Then click the "Info" button
        composeTestRule.onNodeWithContentDescription("Info").performClick()

        // Information text should appear when "Info" button is clicked
        composeTestRule.onNodeWithText("To start a quiz click on the argument's name!").assertExists()
    }

}
