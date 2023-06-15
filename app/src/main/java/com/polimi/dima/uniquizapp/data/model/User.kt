package com.polimi.dima.uniquizapp.data.model

import com.google.gson.annotations.SerializedName

data class User (
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("firstName")
    val firstName: String,
    val lastName: String,
    val universityId: String,
    val subjectIds: List<String>,
    val exams: List<String>,
    val schedules: List<String>
) {

}