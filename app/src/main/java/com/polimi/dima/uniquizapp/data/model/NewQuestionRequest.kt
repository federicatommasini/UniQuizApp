package com.polimi.dima.uniquizapp.data.model

data class NewQuestionRequest(
    val subjectId: String,
    val quizId: String?,
    val quizName: String,
    val question: Question
)
