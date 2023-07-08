package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.Exam
import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.ExamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val examRepo: ExamRepository
): ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    private val _examState = MutableStateFlow<List<Exam?>>(emptyList<Exam>())
    val examState : StateFlow<List<Exam?>> = _examState.asStateFlow()


    suspend fun addExam(userId : String, examRequest : ExamRequest) : User {
        val response = runBlocking {
            examRepo.addExam(userId, examRequest)
        }
        _examState.value = response.exams
        return response
    }
    fun getExams(user : User){
        viewModelScope.launch{
            _examState.value = user.exams
        }
    }


}