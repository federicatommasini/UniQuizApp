package com.polimi.dima.uniquizapp.data.api


import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @GET("/users")
    //suspend fun getUsers() : Response<List<User>>
    suspend fun getUsers() : List<User>
    @GET("/users/{userId}")
    suspend fun getUserById(
        @Path("userId") number : String
    ): Response<User>

    @POST("/login")
    suspend fun login(@Body loginRequest : LoginRequest) : User?
}