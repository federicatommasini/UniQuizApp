package com.polimi.dima.uniquizapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.University
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.UniversityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UniversityViewModel @Inject constructor(
    private val uniRepo: UniversityRepository
): ViewModel() {

    private val _uniState = MutableStateFlow<University?>(null)
    val uniState: StateFlow<University?> = _uniState.asStateFlow()

    private val _allUniState = MutableStateFlow(emptyList<University>())
    val allUniState : StateFlow<List<University>> = _allUniState.asStateFlow()


    fun getUniByName(name : String) : University? {
        viewModelScope.launch{
            val response = runBlocking {  uniRepo.getUniversityByName(name)}
            _uniState.value = response
        }
        return if(_uniState.value == null){
            null
        } else _uniState.value!!
    }

    fun getUniById(id : String) : University? {
        viewModelScope.launch{
            val response = runBlocking { uniRepo.getUniversityById(id)}
            _uniState.value = response


        }
        return if(_uniState.value == null){
            null
        } else return _uniState.value!!
    }

    fun getAllUni() : List<University>{
        viewModelScope.launch {
            val response = runBlocking { uniRepo.getAllUni() }
            _allUniState.value = response

            Log.d("dentro view", _allUniState.value.toString())
        }
        return _allUniState.value
    }

}