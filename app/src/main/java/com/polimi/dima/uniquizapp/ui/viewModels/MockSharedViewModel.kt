package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.api.QuizApi
import com.polimi.dima.uniquizapp.data.di.ApiModule
import com.polimi.dima.uniquizapp.data.model.Answer
import com.polimi.dima.uniquizapp.data.model.Exam
import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.NewQuestionRequest
import com.polimi.dima.uniquizapp.data.model.Question
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.RegistrationRequest
import com.polimi.dima.uniquizapp.data.model.ResponseValidity
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
import kotlinx.datetime.toLocalDate
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class MockSharedViewModel : SharedViewModel(){
    private val uniApi = ApiModule.provideUniversityApi(ApiModule.provideRetrofit())
    private val uniRepo = UniversityRepository(uniApi)
    override val uniViewModel = UniversityViewModel(uniRepo)

    private val userApi = ApiModule.provideUserApi(ApiModule.provideRetrofit())
    private val userRepo = UserRepository(userApi)
    override val userViewModel = UserViewModel(userRepo)

    private val subjectApi = ApiModule.provideSubjectApi(ApiModule.provideRetrofit())
    private val subjectRepo = SubjectRepository(subjectApi)
    override val subjectViewModel = SubjectViewModel(subjectRepo)


    override val quizViewModel = QuizMockViewModel(QuizRepository(ApiModule.provideQuizApi(ApiModule.provideRetrofit())))

    override val examViewModel = ExamMockViewModel(ExamRepository(ApiModule.provideExamApi(ApiModule.provideRetrofit())))

    val dateString = "2023-07-19"
    val format = SimpleDateFormat("yyyy-MM-dd")
    override var user: User? = User("1","username","email@gmail.com","password",
        "John","Doe","Polimi", listOf("1","2","3"),
        listOf(UserExam(Exam("1","2", format.parse(dateString)),null)), "",0,0
    )
    override var points by mutableStateOf(0)
    override var quiz by mutableStateOf<Quiz?>(Quiz("1","quiz1",listOf(Question("questionText",
        listOf(Answer("text1",true),Answer("text2",false),Answer("text3",false),Answer("text4",false)),listOf()))))
    override var subject by mutableStateOf<Subject?>(Subject("1","1234", "subject",listOf("1","2","3"), mapOf(Pair("1",1 as Integer)), "", listOf()))


}

class ExamMockViewModel(examRepo: ExamRepository) :ExamViewModel(examRepo) {

    private val _userState = MutableStateFlow<User?>(null)
    override val userState: StateFlow<User?> = _userState.asStateFlow()
    private val _examState = MutableStateFlow<List<UserExam?>>(emptyList<UserExam>())
    override val examState : StateFlow<List<UserExam?>> = _examState.asStateFlow()
    override suspend fun addExam(userId : String, examRequest : ExamRequest) : User {
        var user = User("1","username","email@gmail.com","password",
            "John","Doe","Polimi", listOf("1","2","3"),
            listOf(UserExam(Exam("1","2", Date("2023-07-19")),null)), "",0,0
        )
        var newList : List<UserExam> = mutableListOf()
        newList += user.exams
        var exam : Exam= Exam("1", examRequest.subjectId, Date(examRequest.date))
        var userExam = UserExam(exam,userId)
        newList += userExam
        var newUser=User(user.id,user.username,user.email,user.password,user.firstName,
            user.lastName,user.universityId, user.subjectIds,newList,user.profilePicUrl,user.questionsAdded,user.questionsReported)
        return newUser
    }
    override fun getExams(user : User){
        _examState.value = user.exams
    }

}

class QuizMockViewModel(quizRepo: QuizRepository) : QuizViewModel(quizRepo){
    val dateString = "2023-07-19"
    val format = SimpleDateFormat("yyyy-MM-dd")
    private val _allQuizzesState = MutableStateFlow(emptyList<Quiz>())

    var score by mutableStateOf(0)
    override val allQuizzesState: StateFlow<List<Quiz>> = _allQuizzesState.asStateFlow()

    private val _allCompletedQuizzesState = MutableStateFlow(emptyList<Quiz?>())
    override val allCompletedQuizzesState: StateFlow<List<Quiz?>> = _allCompletedQuizzesState.asStateFlow()

    override fun getAll(subjectId : String) : List<Quiz> {
        _allQuizzesState.value = listOf(
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
        return allQuizzesState.value
    }
    override fun getQuizById(quizId : String) : Quiz?{
        var quiz = Quiz("1","quiz1",listOf(Question("questionText",
            listOf(Answer("text1",true),Answer("text2",false),Answer("text3",false),Answer("text4",false)),listOf())))
        return quiz
    }

    override fun addScore(quizId: String, userId: String, scored: Int){
        score += scored
    }

    override fun addQuestion(request: NewQuestionRequest) : Subject?{
        var subject: Subject? = null
        val quizzes :List<Quiz> =getAll("1")
        val index = quizzes.indexOf(getQuizById(request.quizId!!))
        val newQuestions : List<Question> = quizzes[index].questions +request.question
        subject = Subject("1","1234","subject1",quizzes.map { it.id},
        mapOf(Pair("1",1 as Integer)), "",listOf())
        return subject
    }

    override fun getQuizzesCompletedByUser(userId : String) : List<Quiz?>{
        val quizzes :List<Quiz> =getAll("1")
        return allCompletedQuizzesState.value
    }

    override fun addReport(quizId : String, index : Int, userId : String, message: String) : User?{
        var user: User? = null
        user = User("1","username","email@gmail.com","password",
            "John","Doe","Polimi", listOf("1","2","3"),
            listOf(UserExam(Exam("1","2", format.parse(dateString)),null)), "",0,1)
        return user
    }
}

class UserMockViewModel(userRepository: UserRepository) : UserViewModel(userRepository){

    override fun login(loginRequest : LoginRequest) : User? {
        val dateString = "2023-07-19"
        val format = SimpleDateFormat("yyyy-MM-dd")
       return User("1","username","email@gmail.com","password",
           "John","Doe","Polimi", listOf("1","2","3"),
           listOf(UserExam(Exam("1","2", format.parse(dateString)),null)), "",0,0
       )
    }

    override fun addSubjectToUser(subject : Subject, userId : String) : User {
        val dateString = "2023-07-19"
        val format = SimpleDateFormat("yyyy-MM-dd")
        return   User("1","username","email@gmail.com","password",
            "John","Doe","Polimi", listOf("1","2","3"),
            listOf(UserExam(Exam("1","2", format.parse(dateString)),null)), "",0,0
        )
    }

    override fun getUserById(userId: String) : User {
        val dateString = "2023-07-19"
        val format = SimpleDateFormat("yyyy-MM-dd")
        return   User("1","username","email@gmail.com","password",
            "John","Doe","Polimi", listOf("1","2","3"),
            listOf(UserExam(Exam("1","2", format.parse(dateString)),null)), "",0,0
        )
    }

}