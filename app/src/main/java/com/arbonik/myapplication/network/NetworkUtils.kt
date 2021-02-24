package com.arbonik.myapplication.network

import com.arbonik.myapplication.network.models.login.UserActivation

fun uriParseToUserActivation(uri : String): UserActivation {
    val afterSplit = uri.split("/")
    val token = afterSplit.last()
    val uid = afterSplit[afterSplit.size - 2]
    val userActivation = UserActivation(token, uid)
    return userActivation
}
