package com.polimi.dima.uniquizapp.data.api


import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.LoginResponse
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.RegistrationRequest
import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("/users")
    suspend fun getUsers() : List<User>
    @GET("/users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId : String
    ): User

    @POST("/login")
    suspend fun login(@Body loginRequest : LoginRequest) : LoginResponse
    @POST("/registration")
    suspend fun register(@Body user: RegistrationRequest): User
    @PUT("/updateProfile/{userId}")
    suspend fun update(@Body pw: String, @Path("userId") userId: String ) : User

    @POST("/{userId}/addSubject")
    suspend fun addSubject(@Body subject : Subject, @Path("userId") userId : String) : User
    @PUT("pic/{userId}")
    suspend fun uploadProfileIcon( @Path("userId") userId: String,@Body url: String) : User

    @GET("/{userId}/points")
    suspend fun getPoints(@Path("userId") userId: String): Int
}