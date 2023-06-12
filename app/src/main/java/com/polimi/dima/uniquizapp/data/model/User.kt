package com.polimi.dima.uniquizapp.data.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val universityId: String,
    val subjectIds: List<String>,
    val exams: List<String>,
    val schedules: List<String>
) {
}