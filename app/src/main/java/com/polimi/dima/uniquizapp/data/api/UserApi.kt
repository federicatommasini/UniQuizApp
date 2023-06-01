package com.polimi.dima.uniquizapp.data.api


import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {

    @GET("/users")
    suspend fun getUsers() :List<User>
}