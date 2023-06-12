package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.RetrofitInstance
import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.Response

class UserRepository{

    suspend fun getUsers(): Response<List<User>> {
        return RetrofitInstance.api.getUsers()
    }

    suspend fun getUserById(userId: String): Response<User> {
        return RetrofitInstance.api.getUserById(userId)
    }
}