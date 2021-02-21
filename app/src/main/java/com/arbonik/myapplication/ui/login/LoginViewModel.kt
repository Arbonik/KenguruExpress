package com.arbonik.myapplication.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.R
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.ui.privateoffice.data.LoginRepository

import com.arbonik.myapplication.network.models.login.UserRequest
import com.arbonik.myapplication.network.models.login.UserResponse
import com.arbonik.myapplication.network.models.login.UserToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    val loginResult : LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch (Dispatchers.IO){
            val result = Common.USER.authefication(UserRequest( username, password)).execute()
            if (result.isSuccessful) {
                LoginRepository.userToken = result.body()!!.auth_token
                //while nouse
                _loginResult.postValue(LoginResult("success"))
            }
            else
            _loginResult.postValue(LoginResult(error = result.message()))
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return (password.length > 7 )
    }
}