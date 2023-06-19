package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.Subject
import retrofit2.http.GET
import retrofit2.http.Path

interface SubjectApi {

    @GET("/subjects")
    suspend fun getSubjects() : List<Subject>

    @GET("/{userId}/subjects")
    suspend fun getSubjectsByUser(@Path("userId") number : String) : List<Subject>

    @GET("/subjectByName/{subjectName}")
    suspend fun getSubjectByName(@Path("subjectName") name : String) : Subject
}