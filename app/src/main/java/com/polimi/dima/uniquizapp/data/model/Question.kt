package com.polimi.dima.uniquizapp.data.model

data class Question(
    val id : String, val content : String, val answers : List<Answer>
)
