package com.polimi.dima.uniquizapp.data.model

data class RegistrationRequest (
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val universityName: String
)