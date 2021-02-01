package com.arbonik.myapplication.network.models.login

data class UserRequest(
    val email: String?,
    val password: String?
)
data class UserAuth(
    val password: String?,
    val email: String?
)