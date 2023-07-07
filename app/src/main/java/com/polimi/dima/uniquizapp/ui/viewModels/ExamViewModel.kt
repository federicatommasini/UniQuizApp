package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun addExam(userId : String, examRequest : ExamRequest) : User {
        var user : User? = null
        viewModelScope.launch{
            var response = runBlocking {
                println("DENTRO VIEW")
                examRepo.addExam(userId, examRequest)
            }
            println("response" + response.toString())
            user = response
            _userState.value = response
        }
        return user!!
        //return _userState.value
    }

}