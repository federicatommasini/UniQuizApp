package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.QuizApi
import com.polimi.dima.uniquizapp.data.model.Quiz
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi
){
    suspend fun getQuizzesBySubject(subjectId : String): List<Quiz> {
        return quizApi.getQuizzesBySubject(subjectId)
    }
}