package com.polimi.dima.uniquizapp.data.api

import com.polimi.dima.uniquizapp.data.model.University
import retrofit2.http.GET
import retrofit2.http.Path

interface UniversityApi {

    @GET("/university/{universityId}")
    suspend fun getUniversityById(
        @Path("universityId") number : String
    ): University

    @GET("/university/{universityName}")
    suspend fun getUniversityByName(
        @Path("universityName") number : String
    ): University

    @GET("/university/all")
    suspend fun getAllUni(): List<University>
}