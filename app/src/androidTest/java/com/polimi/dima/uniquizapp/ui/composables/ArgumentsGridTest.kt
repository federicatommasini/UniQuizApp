package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.annotation.ExperimentalTestApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polimi.dima.uniquizapp.data.model.Answer
import com.polimi.dima.uniquizapp.data.model.Question
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.viewModels.MockSharedViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
@ExperimentalMaterialApi
class ArgumentsGridTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    val navController: NavController = Mockito.mock(NavController::class.java)

    private val sharedViewModel = MockSharedViewModel()


    // Assuming you have a way to create a mock of your Subject.
    private val subject = Subject("1","1234", "subject",listOf("1","2","3"), mapOf(Pair("1",1 as Integer)), "", listOf())

    // This function assumes that you have a way to create a Quiz object for testing.
    private fun createQuizzes() = listOf(
        Quiz("1","quiz1",listOf(Question("questionText",
        listOf(Answer("text1",true),
                Answer("text2",false),
                Answer("text3",false),
                Answer("text4",false)),
        listOf()))), Quiz("2","quiz2",listOf(
            Question("questionText",
                listOf(
                    Answer("text1",true),
                    Answer("text2",false),
                    Answer("text3",false),
                    Answer("text4",false)
                ),listOf()))),
            Quiz("3","quiz3",listOf(
                Question("questionText",
                    listOf(
                        Answer("text1",true),
                        Answer("text2",false),
                        Answer("text3",false),
                        Answer("text4",false)
                    ),listOf()))))

    @Test
    fun testArgumentsGrid_displayed() {
        composeTestRule.setContent {
            ArgumentsGrid(subject = subject, sharedViewModel = sharedViewModel, route = "quiz/", navController = navController)
        }
        composeTestRule.onNodeWithText("quiz2").assertExists()
        composeTestRule.onNodeWithText("quiz3").assertExists()
        composeTestRule.onNodeWithText("quiz1").assertExists()
    }

    @Test
    fun testArgumentsGrid_itemsCount() {
        val quizzes = createQuizzes()

        composeTestRule.setContent {
            ArgumentsGrid(subject = subject, sharedViewModel = sharedViewModel, route = "quiz/", navController = navController)
        }

        // Test if the correct number of items (quizzes) are displayed
        composeTestRule.onAllNodesWithTag("argument")
            .assertCountEquals(quizzes.size)
    }
}
