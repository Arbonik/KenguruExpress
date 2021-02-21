package com.arbonik.myapplication.ui.privateoffice.data

import android.util.Log
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.login.UserActivation
import com.arbonik.myapplication.network.models.login.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {

    // in-memory cache of the loggedInUser object
    var user: UserResponse? = null
        private set

    var token: String? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        //logout()
    }

    fun login(loginUser : UserResponse ){
        this.user = loginUser
    }

    //Функция для подтверждения нового пользовметеля
    fun activatedUser(token : String) {
        Common.USER.activatedUser(UserActivation(token,"MTQ"))
            .enqueue(object : Callback<UserActivation> {
                override fun onResponse(
                    call: Call<UserActivation>,
                    response: Response<UserActivation>
                ) {
                }

                override fun onFailure(call: Call<UserActivation>, t: Throwable) {
                }
            })
    }

}