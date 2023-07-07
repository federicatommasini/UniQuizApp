package com.polimi.dima.uniquizapp.data.repository

import android.util.Log
import com.polimi.dima.uniquizapp.data.api.SubjectApi
import com.polimi.dima.uniquizapp.data.model.Subject
import javax.inject.Inject

class SubjectRepository@Inject constructor(
    private val subjectApi: SubjectApi
) {
    suspend fun getSubjects(): List<Subject> {
        return subjectApi.getSubjects()
    }

    suspend fun getSubjectsByUser(userId : String): List<Subject> {
        return subjectApi.getSubjectsByUser(userId)
    }

    suspend fun getSubjectById(subjectId : String) : Subject {
        return subjectApi.getSubjectById(subjectId)
    }

    suspend fun getDocumentUrls(subjectId: String) : List<String>{
        return subjectApi.getDocumentUrls(subjectId)
    }

    suspend fun updateRanking(subjectId: String, userId: String){
        subjectApi.updateRanking(subjectId,userId)
    }
}