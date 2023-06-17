package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.polimi.dima.uniquizapp.data.di.ApiModule
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.data.repository.UniversityRepository

class SharedViewModel : ViewModel() {

    private val uniApi = ApiModule.provideUniversityApi(ApiModule.provideRetrofit())
    private val uniRepo = UniversityRepository(uniApi)
    val uniViewModel = UniversityViewModel(uniRepo)
    var user by mutableStateOf<User?>(null)
        private set

    fun addUser(newUser : User){
        user = newUser
    }
}