package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.UserApi
import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.LoginResponse
import com.polimi.dima.uniquizapp.data.model.User
import javax.inject.Inject
import retrofit2.Response

class UserRepository @Inject constructor(
    private val userApi: UserApi
){

    suspend fun getUsers(): List<User> {
        return userApi.getUsers()
    }

    suspend fun getUserById(userId: String): Response<User> {
        return userApi.getUserById(userId)
    }

    suspend fun login(loginRequest : LoginRequest) : LoginResponse {
        return userApi.login(loginRequest)
    }
}