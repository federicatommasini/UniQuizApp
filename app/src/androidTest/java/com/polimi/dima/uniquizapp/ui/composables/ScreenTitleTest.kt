package com.polimi.dima.uniquizapp.ui.composables

import android.text.Layout
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenTitleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testScreenTitle_TextIsDisplayed() {
        composeTestRule.setContent {
            ScreenTitle("Test Title")
        }

        composeTestRule.onNodeWithText("Test Title").assertExists()
    }


}
