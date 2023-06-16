package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.Subject
import retrofit2.http.GET

interface SubjectApi {

    @GET("/subjects")
    suspend fun getSubjects() : List<Subject>
}