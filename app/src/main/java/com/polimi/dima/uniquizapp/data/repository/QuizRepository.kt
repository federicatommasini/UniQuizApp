package com.polimi.dima.uniquizapp.data.repository

import com.polimi.dima.uniquizapp.data.api.QuizApi
import com.polimi.dima.uniquizapp.data.model.NewQuestionRequest
import com.polimi.dima.uniquizapp.data.model.Quiz
import com.polimi.dima.uniquizapp.data.model.Subject
import com.polimi.dima.uniquizapp.data.model.User
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi
){
    suspend fun getQuizzesBySubject(subjectId : String): List<Quiz> {
        return quizApi.getQuizzesBySubject(subjectId)
    }

    suspend fun getQuizById(quizId : String): Quiz? {
        return quizApi.getQuizById(quizId)
    }
    suspend fun addScore(quizId : String, userId : String, score : Int){
        quizApi.addScore(quizId,userId,score)
    }
    suspend fun addQuestion(request: NewQuestionRequest) : Subject? {
        return quizApi.addQuestion(request)
    }

    suspend fun getQuizzesCompletedByUser(userId: String) : List<Quiz?>{
        return quizApi.getQuizzesCompletedByUser(userId)
    }
    suspend fun addReport(quizId : String,index : Int, userId : String, message: String): User? {
        return quizApi.addReport(quizId,index,userId,message)
    }
}