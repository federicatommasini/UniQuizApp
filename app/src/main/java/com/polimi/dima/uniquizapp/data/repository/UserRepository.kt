package com.polimi.dima.uniquizapp.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.polimi.dima.uniquizapp.data.api.UserApi
import com.polimi.dima.uniquizapp.data.model.LoginRequest
import com.polimi.dima.uniquizapp.data.model.LoginResponse
import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject
import retrofit2.Response
import java.lang.reflect.Type

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
        var user: User? = null
        userApi.login(loginRequest).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                         user = response.body()

                        Log.d(
                            "dentro la repo",
                            "onResponse: user" + response.body()
                                .toString() + response.body()!!.firstName
                        )
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("dentro la repo fail", "onFailure: ${t.message}")
            }

        })
        return user
    }
}