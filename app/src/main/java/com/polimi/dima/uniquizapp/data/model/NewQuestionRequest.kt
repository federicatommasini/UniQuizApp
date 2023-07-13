package com.polimi.dima.uniquizapp.data.model

data class NewQuestionRequest(
    val userId: String,
    val subjectId: String,
    val quizId: String?,
    val quizName: String,
    val question: Question
)
