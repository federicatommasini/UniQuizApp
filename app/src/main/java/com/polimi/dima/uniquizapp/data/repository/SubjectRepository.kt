package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.SubjectApi
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import javax.inject.Inject

class SubjectRepository@Inject constructor(
    private val subjectApi: SubjectApi
) {
    suspend fun getSubjects(): List<Subject> {
        return subjectApi.getSubjects()
    }
}