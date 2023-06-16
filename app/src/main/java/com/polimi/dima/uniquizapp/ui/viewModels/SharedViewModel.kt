package com.polimi.dima.uniquizapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.polimi.dima.uniquizapp.data.model.User

class SharedViewModel : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    fun addUser(newUser : User){
        user = newUser
    }
}