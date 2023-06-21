package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.Subject
import retrofit2.http.GET
import retrofit2.http.Path

interface SubjectApi {

    @GET("/subjects")
    suspend fun getSubjects() : List<Subject>

    @GET("/{userId}/subjects")
    suspend fun getSubjectsByUser(@Path("userId") number : String) : List<Subject>

    @GET("/subjectById/{subjectId}")
    suspend fun getSubjectById(@Path("subjectId") id : String) : Subject
}