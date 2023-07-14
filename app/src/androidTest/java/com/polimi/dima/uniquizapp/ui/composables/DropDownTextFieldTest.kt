package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMaterialApi::class)
@RunWith(AndroidJUnit4::class)
class DropDownTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // This will be used to capture the lambda function for item selection
    val onItemSelected: (String) -> Unit = {}

    // Creating a FocusRequester for the test
    val focusRequester = FocusRequester()

    // Stub items for dropdown
    val items = listOf("Item 1", "Item 2", "Item 3")

    @Test
    fun testDropDownTextField_isDisplayed() {
        composeTestRule.setContent {
            DropDownTextField(
                text = "Test",
                items = items,
                selectedItem = "Item 1",
                onItemSelected = onItemSelected,
                focusRequester = focusRequester,
                fraction = 1f
            )
        }

        // Check if TextField is displayed with the correct text
        composeTestRule.onNodeWithText("Test").assertExists()
    }

    @Test
    fun testDropDownTextField_isDropDownMenuDisplayedWhenClicked() {
        composeTestRule.setContent {
            DropDownTextField(
                text = "Test",
                items = items,
                selectedItem = "Test",
                onItemSelected = onItemSelected,
                focusRequester = focusRequester,
                fraction = 1f
            )
        }

        composeTestRule.onNodeWithText("Test").performClick()

        composeTestRule.onNodeWithText("Item 1").assertExists()
        composeTestRule.onNodeWithText("Item 2").assertExists()
        composeTestRule.onNodeWithText("Item 3").assertExists()
    }

    @Test
    fun testDropDownTextField_isSelectedItemDisplayedWhenClicked() {
        composeTestRule.setContent {
            DropDownTextField(
                text = "Test",
                items = items,
                selectedItem = "Item 1",
                onItemSelected = onItemSelected,
                focusRequester = focusRequester,
                fraction = 1f
            )
        }

        // Click on TextField
        composeTestRule.onNodeWithText("Test").performClick()

        // Click on an item in the DropdownMenu
        composeTestRule.onNodeWithText("Item 3").performClick()

        // Check if the selected item is now displayed in the TextField
        composeTestRule.onNodeWithText("Item 3").assertExists()
    }
}
