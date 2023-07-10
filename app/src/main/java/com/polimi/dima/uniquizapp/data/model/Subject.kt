package com.polimi.dima.uniquizapp.data.model

data class Subject (
    val id: String,
    val code: String,
    val name: String,
    val quizIds: List<String>,
    val ranking:  Map<String, Integer>,
    val base_url: String,
    val pdf_links:  List<String>
)