package com.polimi.dima.uniquizapp.data.model

data class LoginResponse(
    val validity: ResponseValidity,
    val user: User
    )