package com.arbonik.myapplication.ui.privateoffice.data.model

data class UserRequest(
    val email: String?,
    val password: String?
)
data class UserAuth(
    val password: String?,
    val email: String?
)