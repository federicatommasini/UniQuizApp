package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.RetrofitInstance
import com.polimi.dima.uniquizapp.data.model.User

class Repository {

    suspend fun getUsers(): List<User> {
        return RetrofitInstance.api.getUsers()
    }
}