package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.polimi.dima.uniquizapp.data.di.ApiModule
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.ExamRepository
import com.polimi.dima.uniquizapp.data.repository.QuizRepository
import com.polimi.dima.uniquizapp.data.repository.SubjectRepository
import com.polimi.dima.uniquizapp.data.repository.UniversityRepository
import com.polimi.dima.uniquizapp.data.repository.UserRepository

class SharedViewModel : ViewModel() {

    private val uniApi = ApiModule.provideUniversityApi(ApiModule.provideRetrofit())
    private val uniRepo = UniversityRepository(uniApi)
    val uniViewModel = UniversityViewModel(uniRepo)

    private val userApi = ApiModule.provideUserApi(ApiModule.provideRetrofit())
    private val userRepo = UserRepository(userApi)
    val userViewModel = UserViewModel(userRepo)

    private val subjectApi = ApiModule.provideSubjectApi(ApiModule.provideRetrofit())
    private val subjectRepo = SubjectRepository(subjectApi)
    val subjectViewModel = SubjectViewModel(subjectRepo)
    var documentUrl by mutableStateOf<String?>(null)

    private val quizApi = ApiModule.provideQuizApi(ApiModule.provideRetrofit())
    private val quizRepo = QuizRepository(quizApi)
    val quizViewModel = QuizViewModel(quizRepo)

    private val examApi = ApiModule.provideExamApi(ApiModule.provideRetrofit())
    private val examRepo = ExamRepository(examApi)
    val examViewModel = ExamViewModel(examRepo)

    var user by mutableStateOf<User?>(null)
        private set

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

    fun logout(){
        user = null
    }
}