package com.polimi.dima.uniquizapp.data.repository

import android.util.Log
import com.polimi.dima.uniquizapp.data.api.UniversityApi
import com.polimi.dima.uniquizapp.data.model.University

import javax.inject.Inject

class UniversityRepository @Inject constructor(
    private val uniApi: UniversityApi ){

    suspend fun getUniversityById(universityId: String): University {
        return uniApi.getUniversityById(universityId)
    }

    suspend fun getUniversityByName(universityName: String): University {
        return uniApi.getUniversityByName(universityName)
    }

    suspend fun getAllUni() : List<University> {
        return uniApi.getAllUni()

    }
}