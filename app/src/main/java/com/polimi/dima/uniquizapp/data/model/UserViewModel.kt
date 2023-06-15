package com.polimi.dima.uniquizapp.data.model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {

    private val _allUsersState = MutableStateFlow(emptyList<User>())
    val allUsersState: StateFlow<List<User>> = _allUsersState.asStateFlow()

    private val _loginState = MutableStateFlow<User?>(null)
    val loginState: StateFlow<User?> = _loginState.asStateFlow()

    init{
        viewModelScope.launch {
            val users = userRepo.getUsers()
            _allUsersState.value = users
        }
    }

    fun getUsers(){
        viewModelScope.launch{
            val response = userRepo.getUsers()
            _allUsersState.value = response
        }
    }

    /*fun login(loginRequest : LoginRequest) : User? {
        viewModelScope.launch{
            val response = runBlocking { userRepo.login(loginRequest)}
            if(response.validity==ResponseValidity.VALID)
                _loginState.value = response.user
            else _loginState.value = null
        }
        if(_loginState.value==null)
            return null
        else return _loginState.value
    }*/

    fun login(loginRequest: LoginRequest) : User? {
        viewModelScope.launch{
            val response = runBlocking { userRepo.login(loginRequest)}
            _loginState.value = response
            /*if(response.validity==ResponseValidity.VALID)
                _loginState.value = response.user
            else _loginState.value = null*/
        }
        /*if(_loginState.value==null)
            return null
        else return _loginState.value*/
        return _loginState.value
    }

    fun getState() : List<User> {
        return allUsersState.value
    }

}