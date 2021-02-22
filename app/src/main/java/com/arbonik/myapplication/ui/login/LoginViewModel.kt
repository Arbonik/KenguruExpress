package com.arbonik.myapplication.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.R
import com.arbonik.myapplication.network.Common

import com.arbonik.myapplication.network.models.login.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val loginRepository = KenguruApplication.loginRepository

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    val loginResult : LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch (Dispatchers.IO){
            val result = loginRepository.login(UserRequest( username, password))
            _loginResult.postValue(result)
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