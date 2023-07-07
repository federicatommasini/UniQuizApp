package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.RegistrationRequest
import com.polimi.dima.uniquizapp.data.model.ResponseValidity
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
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

    fun login(loginRequest : LoginRequest) : User? {
        viewModelScope.launch{
            val response = runBlocking { userRepo.login(loginRequest)}
            if(response.validity== ResponseValidity.VALID)
                _loginState.value = response.user
            else _loginState.value = null
        }
        if(_loginState.value == null)
            return null
        else return _loginState.value
    }

    fun updateProfile(user : User, userId: String) : User? {
        viewModelScope.launch {
            var response = runBlocking { userRepo.updateProfile(user, userId) }
            _loginState.value = response
        }
        return _loginState.value
    }

    fun register(user: RegistrationRequest) : User? {
        viewModelScope.launch {
            val response = runBlocking {
                userRepo.register(user)
            }
            _loginState.value = response
        }
        return _loginState.value
    }

    fun addSubjectToUser(subject : Subject, userId : String) : User {
        var user : User? = null
        viewModelScope.launch{
            user = runBlocking { userRepo.addSubject(subject, userId) }
        }
        return user!!
    }

    fun uploadProfileIcon(user: User, userId: String) : User? {
        viewModelScope.launch {
            var response = runBlocking { userRepo.uploadProfileIcon(user, userId) }
            _loginState.value = response
        }
        return _loginState.value
    }
}