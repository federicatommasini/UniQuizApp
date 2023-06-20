package com.polimi.dima.uniquizapp.data.model

data class RegistrationRequest (
    var username: String,
    var email: String,
    var password: String,
    val firstName: String,
    val lastName: String,
    var universityName: String
)