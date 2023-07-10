package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.Subject
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SubjectApi {

    @GET("/subjects")
    suspend fun getSubjects() : List<Subject>

    @GET("/{id}/subjects")
    suspend fun getSubjectsByUser(@Path("id") id : String) : List<Subject>

    @GET("/subjectById/{subjectId}")
    suspend fun getSubjectById(@Path("subjectId") id : String) : Subject


    suspend fun getDocumentUrls(@Path("subjectId") id : String) : List<String>

    @PUT("/updateRanking/{subjectId}/{userId}")
    suspend fun updateRanking(@Path("subjectId") subjectId : String, @Path("userId") userId : String)
    @GET("/completedSubjects/{userId}")
    suspend fun completedSubjectsUser(@Path("userId") userId: String) : Int
}