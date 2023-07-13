package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PopUpTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPopUp_TitleIsDisplayed() {
        val isPopupVisible = mutableStateOf(true)
        composeTestRule.setContent {
            PopUp("Test Title", "Test Text", "Test Button", isPopupVisible)
        }

        composeTestRule.onNodeWithText("Test Title").assertExists()
    }

    @Test
    fun testPopUp_TextIsDisplayed() {
        val isPopupVisible = mutableStateOf(true)
        composeTestRule.setContent {
            PopUp("Test Title", "Test Text", "Test Button", isPopupVisible)
        }

        composeTestRule.onNodeWithText("Test Text").assertExists()
    }

    @Test
    fun testPopUp_ButtonTextIsDisplayed() {
        val isPopupVisible = mutableStateOf(true)
        composeTestRule.setContent {
            PopUp("Test Title", "Test Text", "Test Button", isPopupVisible)
        }

        composeTestRule.onNodeWithText("Test Button").assertExists()
    }

    @Test
    fun testPopUp_DismissDialog() {
        val isPopupVisible = mutableStateOf(true)
        composeTestRule.setContent {
            PopUp("Test Title", "Test Text", "Test Button", isPopupVisible)
        }

        composeTestRule.onNodeWithText("Test Button").performClick()
        Truth.assertThat(isPopupVisible.value).isFalse()
    }
}
