package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import com.polimi.dima.uniquizapp.data.di.ApiModule
import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.ExamRepository
import com.polimi.dima.uniquizapp.data.repository.QuizRepository
import com.polimi.dima.uniquizapp.data.repository.SubjectRepository
import com.polimi.dima.uniquizapp.data.repository.UniversityRepository
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class SharedViewModel : ViewModel() {

    private val uniApi = ApiModule.provideUniversityApi(ApiModule.provideRetrofit())
    private val uniRepo = UniversityRepository(uniApi)
    open val uniViewModel = UniversityViewModel(uniRepo)

    private val userApi = ApiModule.provideUserApi(ApiModule.provideRetrofit())
    private val userRepo = UserRepository(userApi)
    open val userViewModel = UserViewModel(userRepo)

    private val subjectApi = ApiModule.provideSubjectApi(ApiModule.provideRetrofit())
    private val subjectRepo = SubjectRepository(subjectApi)
    open val subjectViewModel = SubjectViewModel(subjectRepo)
    var documentUrl by mutableStateOf<String?>(null)

    private val quizApi = ApiModule.provideQuizApi(ApiModule.provideRetrofit())
    private val quizRepo = QuizRepository(quizApi)
    open val quizViewModel = QuizViewModel(quizRepo)

    private val examApi = ApiModule.provideExamApi(ApiModule.provideRetrofit())
    private val examRepo = ExamRepository(examApi)
    open val examViewModel = ExamViewModel(examRepo)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadEvents(){
        viewModelScope.launch {
            _isLoading.value = true
            delay(3000)
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                var pageTokenEvents: String? = null
                do {
                    val eventsList: Events =
                        calendarService!!.events().list(calendarId)
                            .setPageToken(pageTokenEvents)
                            .execute()
                    val eventItems: List<Event> = eventsList.items
                    subjectViewModel.getSubjectsByUser(user!!.id)
                    for (event in eventItems) {
                        val subjectNameInCalendar = event.summary
                        for (subject in subjectViewModel.userSubjectsState.value) {
                            if (subject!!.name.compareTo(subjectNameInCalendar, ignoreCase = true) == 0) {
                                val startDate = event.start
                                val day = if (startDate.dateTime != null) {
                                    startDate.dateTime.toString()
                                } else {
                                    startDate.date.toString()
                                }
                                //if in the calendar there is an exam of a subject I added on uniquiz, I send the request to the backend
                                val examRequest = ExamRequest(subject.id, day!!, event.description)
                                val newUser = runBlocking {
                                    examViewModel.addExam(user!!.id, examRequest)
                                }
                                addUser(newUser)
                            }
                        }
                    }
                    pageTokenEvents = eventsList.nextPageToken
                } while (pageTokenEvents != null)
            }
            _isLoading.value = false
        }
    }

    open var user by mutableStateOf<User?>(null)

    var googleAccount by mutableStateOf<GoogleSignInAccount?>(null)
        private set

    var calendarService by mutableStateOf<Calendar?>(null)
        private set

    var calendarId by mutableStateOf<String?>(null)
        private set

    open var points by mutableStateOf(0)
    open var quiz by mutableStateOf<Quiz?>(null)
    open var subject by mutableStateOf<Subject?>(null)

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
        googleAccount = null
        calendarService = null
        calendarId = null
    }

    fun addGoogleAccount(newGoogleAccount : GoogleSignInAccount){
        googleAccount = newGoogleAccount
    }

    fun addCalendarService(newCalendarService : Calendar){
        calendarService = newCalendarService
    }

    fun addCalendarId(newCalendarId : String){
        calendarId = newCalendarId
    }
}