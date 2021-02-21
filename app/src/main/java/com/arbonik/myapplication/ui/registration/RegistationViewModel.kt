package com.arbonik.myapplication.ui.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.login.UserRequest
import com.arbonik.myapplication.ui.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistationViewModel : ViewModel() {

    val registrationStatus = MutableLiveData<LoginResult>()

    fun registrationNewUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = Common.USER.createUser(UserRequest(email, password)).execute()
            if (response.isSuccessful) {
                val newUser = response.body()
                Log.d("NewUser", newUser.toString())
                registrationStatus.postValue(LoginResult("Вы успешно зарегестрированы, активируйте аккаунт на почте"))
            } else {
                registrationStatus.postValue(LoginResult(error = response.message()))
            }
        }
    }

}