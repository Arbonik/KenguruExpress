package com.arbonik.myapplication.ui.privateoffice.data

import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.login.UserActivation
import com.arbonik.myapplication.network.models.login.UserRequest

object LoginRepository {

    var userToken: String? = null

    val isLoggedIn: Boolean
        get() = userToken != null

    fun login(loginUser : UserRequest ) : Boolean{
        val result = Common.USER.authefication(loginUser).execute()
        if (result.isSuccessful){
            userToken = result.body()?.auth_token
        }
        return result.isSuccessful
    }

    fun logout() {
        userToken = null
    }

    //Функция для подтверждения нового пользовметеля
    fun activatedUser(userActivation: UserActivation) : Boolean {
        val result = Common.USER.activatedUser(userActivation).execute()
        if (result.isSuccessful){
            userToken = result.body()?.token
        }
        return result.isSuccessful
    }
}