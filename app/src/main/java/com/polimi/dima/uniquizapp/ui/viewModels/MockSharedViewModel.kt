package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.di.ApiModule
import com.polimi.dima.uniquizapp.data.model.Exam
import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.model.UserExam
import com.polimi.dima.uniquizapp.data.repository.ExamRepository
import com.polimi.dima.uniquizapp.data.repository.QuizRepository
import com.polimi.dima.uniquizapp.data.repository.SubjectRepository
import com.polimi.dima.uniquizapp.data.repository.UniversityRepository
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject

class MockSharedViewModel {
    private val uniApi = ApiModule.provideUniversityApi(ApiModule.provideRetrofit())
    private val uniRepo = UniversityRepository(uniApi)
    val uniViewModel = UniversityViewModel(uniRepo)

    private val userApi = ApiModule.provideUserApi(ApiModule.provideRetrofit())
    private val userRepo = UserRepository(userApi)
    val userViewModel = UserViewModel(userRepo)

    private val subjectApi = ApiModule.provideSubjectApi(ApiModule.provideRetrofit())
    private val subjectRepo = SubjectRepository(subjectApi)
    val subjectViewModel = SubjectViewModel(subjectRepo)

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

class ExamMockViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()
    private val _examState = MutableStateFlow<List<UserExam?>>(emptyList<UserExam>())
    val examState : StateFlow<List<UserExam?>> = _examState.asStateFlow()
    suspend fun addExam(userId : String, examRequest : ExamRequest) : User {
        var user = User("1","username","email@gmail.com","password",
            "John","Doe","Polimi", listOf("1","2","3"),
            listOf(UserExam(Exam("1","2", Date("2023-07-19")),null)), "",0,0
        )
        var newList : List<UserExam> = mutableListOf()
        newList += user.exams
        var exam : Exam= Exam("1", examRequest.subjectId, Date(examRequest.date))
        var userExam = UserExam(exam,userId)
        newList += userExam
       // user.exams = newList
        return user
    }
    fun getExams(user : User){
        _examState.value = user.exams
    }

}