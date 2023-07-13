package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgressBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customProgressBarDisplayedTest() {
        composeTestRule.setContent {
            CustomProgressBar(progress = 0.5f)
        }
        composeTestRule.onNodeWithTag("CustomProgressBar").assertExists()
    }
}
