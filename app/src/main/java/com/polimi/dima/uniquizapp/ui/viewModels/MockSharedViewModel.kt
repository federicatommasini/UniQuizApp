package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.polimi.dima.uniquizapp.data.di.ApiModule
import com.polimi.dima.uniquizapp.data.model.Exam
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.model.UserExam
import com.polimi.dima.uniquizapp.data.repository.ExamRepository
import com.polimi.dima.uniquizapp.data.repository.QuizRepository
import java.util.Date

class MockSharedViewModel {

    private val quizApi = ApiModule.provideQuizApi(ApiModule.provideRetrofit())
    private val quizRepo = QuizRepository(quizApi)
    val quizViewModel = QuizViewModel(quizRepo)

    private val examApi = ApiModule.provideExamApi(ApiModule.provideRetrofit())
    private val examRepo = ExamRepository(examApi)
    val examViewModel = ExamViewModel(examRepo)

    var user = User("1","username","email@gmail.com","password",
        "John","Doe","Polimi", listOf("1","2","3"),
        listOf(UserExam(Exam("1","2", Date("2023-07-19")),null)), "",0,0
    )
    var documentUrl by mutableStateOf<String?>(null)
    var points by mutableStateOf(0)
    var quiz by mutableStateOf<Quiz?>(null)
    var subject by mutableStateOf<Subject?>(null)

    fun addUser(newUser : User){
        user = newUser
    }
    fun addQuiz(newQuiz : Quiz){
        quiz = newQuiz
    }
    fun addSubject(newsub : Subject){
        subject = newsub
    }

    fun addUrl(newUrl: String){
        documentUrl = newUrl
    }

    fun addPoint(){
        points +=1
    }


    fun resetPoints(){
        points = 0
    }


}