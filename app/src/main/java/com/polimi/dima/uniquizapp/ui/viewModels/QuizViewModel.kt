package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.NewQuestionRequest
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.repository.QuizRepository
import com.polimi.dima.uniquizapp.data.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepo: QuizRepository
): ViewModel() {

    private val _allQuizzesState = MutableStateFlow(emptyList<Quiz>())
    val allQuizzesState: StateFlow<List<Quiz>> = _allQuizzesState.asStateFlow()

    fun getAll(subjectId : String) : List<Quiz> {
        viewModelScope.launch{
            val response = runBlocking {
                quizRepo.getQuizzesBySubject(subjectId)
            }
            _allQuizzesState.value = response
        }
        return allQuizzesState.value
    }
    fun getQuizById(quizId : String) : Quiz?{
        var quiz : Quiz? = null
        viewModelScope.launch{
            val response = runBlocking { quizRepo.getQuizById(quizId) }
            quiz = response
        }
        return quiz
    }

    fun addScore(quizId: String, userId: String, score: Int){
        viewModelScope.launch{
            val response = runBlocking {quizRepo.addScore(quizId,userId,score)}
        }
    }

    fun addQuestion(request: NewQuestionRequest) : Subject?{
        var subject: Subject? = null
        viewModelScope.launch{
            val response = runBlocking{quizRepo.addQuestion(request)}
            subject = response
        }
        return subject
    }
}