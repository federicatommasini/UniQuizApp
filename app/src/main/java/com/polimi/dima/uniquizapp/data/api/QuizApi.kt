package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface QuizApi {

    @GET("/quizzes/{subjectId}")
    suspend fun getQuizzesBySubject(@Path("subjectId") id : String) : List<Quiz>

    @GET("/quizById/{quizId}")
    suspend fun getQuizById(@Path("quizId") id : String) : Quiz?

    @PUT("/addScore/{quizId}/{userId}/{score}")
    suspend fun addScore(@Path("quizId") quizId : String, @Path("userId") userId : String, @Path("score") score : Int)

}