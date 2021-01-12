package com.arbonik.myapplication.model.login

data class UserRequest(
    val email: String?,
    val password: String?
)
data class UserAuth(
    val password: String?,
    val email: String?
)