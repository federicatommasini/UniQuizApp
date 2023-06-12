package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

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
}