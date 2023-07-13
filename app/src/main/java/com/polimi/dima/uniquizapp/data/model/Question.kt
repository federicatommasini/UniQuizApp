package com.polimi.dima.uniquizapp.data.model

data class Question(
    val content : String, val answers : List<Answer>, val reports: List<String>
)
