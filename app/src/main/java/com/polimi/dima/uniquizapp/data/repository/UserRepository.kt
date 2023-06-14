package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.UserApi
import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.User
import javax.inject.Inject
import retrofit2.Response

class UserRepository @Inject constructor(
    private val userApi: UserApi
){

    /*suspend fun getUsers(): Response<List<User>> {
        return RetrofitInstance.api.getUsers()
    }*/

    suspend fun getUsers(): List<User> {
        return userApi.getUsers()
        //return RetrofitInstance.api.getUsers()
    }

    suspend fun getUserById(userId: String): Response<User> {
        return userApi.getUserById(userId)
    }

    suspend fun login(loginRequest : LoginRequest) : User? {
        return userApi.login(loginRequest)
    }
}