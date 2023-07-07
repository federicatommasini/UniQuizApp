package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.ExamApi
import com.polimi.dima.uniquizapp.data.model.Exam
import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.User
import javax.inject.Inject

class ExamRepository @Inject constructor(
    private val examApi: ExamApi
) {

    suspend fun addExam(userId : String, exam : ExamRequest) : User {
        println("REPO")
        return examApi.addExam(exam, userId)
    }
}