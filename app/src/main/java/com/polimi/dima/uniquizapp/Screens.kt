package com.polimi.dima.uniquizapp

sealed class Screen(val route: String){
    object Login: Screen(route = "login_screen")
    object SignUp: Screen(route = "signup_screen")
    object Profile: Screen(route = "profile_screen")
    object SubjectScreen: Screen(route = "subject_screen/{subjectId}")
    object Pdf: Screen(route = "pdf")
    object QuizScreen: Screen(route = "quiz/{quizId}/{questionId}")

    object EndOfQuizScreen : Screen(route = "endOfQuiz")

    object AddQuestionScreen : Screen(route = "addQuestionScreen")
}
