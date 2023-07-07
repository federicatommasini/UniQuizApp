package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ExamApi {

    @POST("/addExam/{userId}")
    suspend fun addExam(@Body exam : ExamRequest, @Path("userId") number : String) : User
}