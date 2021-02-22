package com.arbonik.myapplication.repositories

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.login.ProfileData
import com.arbonik.myapplication.network.models.login.UserActivation
import com.arbonik.myapplication.network.models.login.UserRequest
import com.arbonik.myapplication.ui.login.LoginResult
import retrofit2.Response

class UserRepository(context: Context) {

    private val USER_TOKEN_KEY = "userToken"

    @RequiresApi(Build.VERSION_CODES.N)
    val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    var userToken: String? = null
    var userData: ProfileData? = null

    val isLoggedIn: Boolean
        get() = userToken != null

    init {
        userToken = sharedPreferences.getString(USER_TOKEN_KEY, null)
        Log.d("init from SP", userToken.toString())
    }

    fun login(loginUser : UserRequest ) : LoginResult{
        val result = Common.USER.authefication(loginUser).execute()
        if (result.isSuccessful){
            userToken = result.body()?.auth_token
            Log.d("token save to SP", userToken.toString())
            sharedPreferences.edit()
                .putString(USER_TOKEN_KEY, userToken)
                .apply()
            return LoginResult("success")
        }
        return LoginResult(error = result.message())
    }

    fun logout() {
        userToken = null
        userData = null
        sharedPreferences.edit()
            .putString("userToken", null)
            .apply()
    }

    //Функция для подтверждения нового пользовметеля
    fun activatedUser(userActivation: UserActivation) : Boolean {
        val result = Common.USER.activatedUser(userActivation).execute()
        if (result.isSuccessful){
            userToken = result.body()?.token
        }
        return result.isSuccessful
    }

    fun loadProfileData(): Boolean {
        val result = Common.USER.getUserData(userToken!!).execute()
        if (result.isSuccessful){
            userData = result.body()
        }
        return result.isSuccessful
    }

    fun updateProfileData(newData: ProfileData): Response<ProfileData> {
        val result = Common.USER.updateUserData(userToken!!, newData).execute()
        if (result.isSuccessful){
            userData = result.body()
        }
        else
        {
            Log.d("BAD", result.message())
            Log.d("BAD", userToken!!)
            Log.d("BAD", result.code().toString())
        }
        return result
    }

}