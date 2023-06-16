package com.polimi.dima.uniquizapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.University
import com.polimi.dima.uniquizapp.data.repository.UniversityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UniversityViewModel @Inject constructor(
    private val uniRepo: UniversityRepository
): ViewModel() {

    private val _uniState = MutableStateFlow<University?>(null)
    val uniState: StateFlow<University?> = _uniState.asStateFlow()

    fun getUniByName(name : String) : University? {
        viewModelScope.launch{
            val response = uniRepo.getUniversityByName(name)
            _uniState.value = response
        }
        return if(_uniState.value == null){
            null
        } else _uniState.value!!
    }

    fun getUniById(id : String) : University? {
        viewModelScope.launch{
            val response = uniRepo.getUniversityById(id)
            Log.d("uniModel", response.name)
            Log.d("unimodel2", response.toString())
            _uniState.value = response
            Log.d("unimodel3", _uniState.value.toString())


        }
        return if(_uniState.value == null){
            Log.d("unimodel3", "dentro l'if")
            null
        } else return _uniState.value!!
    }

}