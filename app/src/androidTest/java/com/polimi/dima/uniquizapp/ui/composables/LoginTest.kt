package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polimi.dima.uniquizapp.BottomNavItem
import com.polimi.dima.uniquizapp.Screen
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Mock
    val navController: NavController = mock(NavController::class.java)
    @Mock
    val sharedViewModel: SharedViewModel = mock(SharedViewModel::class.java)

    @Test
    fun loginFields_areDisplayed() {
        // Act
        composeTestRule.setContent {
            Login(navController = navController, sharedViewModel = sharedViewModel)
        }

        // Assert
        composeTestRule.onNodeWithText("Email Address").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create An Account").assertIsDisplayed()
        composeTestRule.onNode(hasText("Login") and hasClickAction()).assertIsDisplayed()
        composeTestRule.onNode(hasText("Login") and !hasClickAction()).assertIsDisplayed()
    }

}
