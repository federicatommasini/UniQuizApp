package com.polimi.dima.uniquizapp.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {
    private val _state = MutableStateFlow(emptyList<User>())
    val state: StateFlow<List<User>> = _state.asStateFlow()

        //get() = _state

    /*init{
        viewModelScope.launch {
            val users = userRepo.getUsers()
            _state.value = users
        }
    }*/

    fun getUsers(){
        viewModelScope.launch{
            val response = userRepo.getUsers()
            _state.value = response
        }
    }

} /*private val repository: UserRepository): ViewModel() {

    val allUserResponse: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val userByIdResponse: MutableLiveData<Response<User>> = MutableLiveData()

    fun getUsers(){
        viewModelScope.launch{
            val response = repository.getUsers()
            allUserResponse.value = response
        }
    }

    fun getUserById(userId: String){
        viewModelScope.launch{
            val response = repository.getUserById(userId)
            userByIdResponse.value = response
        }
    }
}*/