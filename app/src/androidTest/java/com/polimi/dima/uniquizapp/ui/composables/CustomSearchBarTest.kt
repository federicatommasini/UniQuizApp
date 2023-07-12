package com.polimi.dima.uniquizapp.ui.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class CustomSearchBarTest{
    @get:Rule
    val composeTestRule = createComposeRule()
    @Mock
    val navController: NavController = Mockito.mock(NavController::class.java)
    @Mock
    val sharedViewModel: SharedViewModel = Mockito.mock(SharedViewModel::class.java)

    @Test
    fun custom_searchbar_isDisplayed(){
        val items : List<Subject> = listOf(
            Subject( id="1", code="1234", name="subj1", quizIds = listOf("1","2","3"),
                ranking= mapOf(Pair("1",Integer(2)),Pair("2",Integer(1))), base_url = "",
                 pdf_links = listOf()))


    }
}