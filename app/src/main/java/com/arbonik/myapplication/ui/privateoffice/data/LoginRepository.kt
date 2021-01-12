package com.arbonik.myapplication.ui.privateoffice.data

import com.arbonik.myapplication.model.login.UserResponse

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

object LoginRepository {

    // in-memory cache of the loggedInUser object
    var user: UserResponse? = null
        private set

    var token: String? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        //logout()
    }

    fun login(loginUser : UserResponse ){
        // handle login
        setLoggedInUser(loginUser)
    }

    private fun setLoggedInUser(loggedInUser: UserResponse) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}