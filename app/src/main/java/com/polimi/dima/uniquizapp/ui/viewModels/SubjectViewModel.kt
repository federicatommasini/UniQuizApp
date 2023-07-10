package com.polimi.dima.uniquizapp.ui.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.SubjectRepository
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Optional.empty
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepo: SubjectRepository
): ViewModel() {
    private val _allSubjectsState = MutableStateFlow(emptyList<Subject>())
    val allSubjectsState: StateFlow<List<Subject>> = _allSubjectsState.asStateFlow()


    private val _userSubjectsState = MutableStateFlow(emptyList<Subject>())
    val userSubjectsState: StateFlow<List<Subject>> = _userSubjectsState.asStateFlow()

    /*init{
        viewModelScope.launch {
            val subjects = subjectRepo.getSubjects()
            _allSubjectsState.value = subjects
        }
    }*/

   fun getState() : List<Subject> {
       viewModelScope.launch{
           val response = runBlocking {
               subjectRepo.getSubjects()
           }
           _allSubjectsState.value = response
       }
       return allSubjectsState.value
   }

    fun getSubjectsByUser(userId : String) : List<Subject>{
        viewModelScope.launch {
            val response = runBlocking { subjectRepo.getSubjectsByUser(userId) }
            _userSubjectsState.value = response
        }
        return userSubjectsState.value
    }

    fun getSubjectById(subjectId : String) : Subject?{
        var subject : Subject? = null
        viewModelScope.launch{
            val response = runBlocking { subjectRepo.getSubjectById(subjectId) }
            subject = response
        }
        return subject
    }

    fun getDocumentUrls(subjectId : String) : List<String>?{
        var urls : List<String>? = null
        viewModelScope.launch {
            val response = runBlocking { subjectRepo.getDocumentUrls(subjectId) }
            urls = response
        }
        return urls
    }

    fun updateRanking(subjectId: String, userId: String){
        viewModelScope.launch {
            val response = runBlocking { subjectRepo.updateRanking(subjectId,userId)}
        }
    }

}