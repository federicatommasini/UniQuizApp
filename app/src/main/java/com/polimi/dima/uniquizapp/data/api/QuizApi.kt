package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import retrofit2.http.GET
import retrofit2.http.Path

interface QuizApi {

    @GET("/quizzes/{subjectId}")
    suspend fun getQuizzesBySubject(@Path("subjectId") id : String) : List<Quiz>
}