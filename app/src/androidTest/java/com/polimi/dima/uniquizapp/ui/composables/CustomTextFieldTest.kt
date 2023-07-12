package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class ComposablesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customTextField_FieldValueChanges() {
        val fieldText = mutableStateOf("")

        composeTestRule.setContent {
            CustomTextField(
                field = fieldText,
                nameField = "Name",
                customImageVector = null,
                focusRequester = FocusRequester(),
                keyboardActions = KeyboardActions.Default,
                fraction = 0.8f
            )
        }

        composeTestRule.onNodeWithTag("customTextField")
            .performTextInput("John Doe")

        assertEquals("John Doe", fieldText.value)
    }
}
