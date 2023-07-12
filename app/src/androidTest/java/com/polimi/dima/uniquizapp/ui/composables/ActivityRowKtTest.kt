package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class ActivityRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun activityRow_withProgressBar_isDisplayed() {
        // Arrange
        val paddingValues = PaddingValues(8.dp)
        val header = "Activity"
        val text = "In Progress"
        val fractionCompleted = 0.5f
        val progressBar = true

        // Act
        composeTestRule.setContent {
            ActivityRow(
                paddingValues = paddingValues,
                header = header,
                text = text,
                fractionCompleted = fractionCompleted,
                progressBar = progressBar
            )
        }

        // Assert
        composeTestRule.onNodeWithText(header).assertIsDisplayed()
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithTag("CustomProgressBar").assertIsDisplayed()
    }

    @Test
    fun activityRow_withoutProgressBar_isDisplayed() {
        // Arrange
        val paddingValues = PaddingValues(8.dp)
        val header = "Activity"
        val text = "Completed"
        val fractionCompleted = null
        val progressBar = false

        // Act
        composeTestRule.setContent {
            ActivityRow(
                paddingValues = paddingValues,
                header = header,
                text = text,
                fractionCompleted = fractionCompleted,
                progressBar = progressBar
            )
        }

        // Assert
        composeTestRule.onNodeWithText(header).assertIsDisplayed()
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithTag("CustomProgressBar").assertDoesNotExist()
    }
}
