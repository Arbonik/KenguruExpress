package com.arbonik.myapplication.ui.privateoffice.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arbonik.myapplication.R
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.ui.privateoffice.data.LoginRepository
import com.arbonik.myapplication.network.models.login.UserAuth
import com.arbonik.myapplication.network.models.login.UserRequest
import com.arbonik.myapplication.network.models.login.UserResponse
import com.arbonik.myapplication.network.models.login.UserToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val _loginResult = MutableLiveData<LoginResult>()

    fun login(username: String, password: String) {
// test data
        val user = UserRequest("zmear@mail.ru", "1234Test")

        auth(user.email!!, user.password!!)
    }

    private fun auth(username: String, password: String) {
        Common.USER.authefication(UserAuth(password, username))
                .enqueue(object : Callback<UserToken> {

                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                        Log.d("RETROFIT", "auth success")
                        LoginRepository.token = response.body()?.auth_token
                        Log.d("RETROFIT", response.body().toString())
                        Log.d("RETROFIT", response.errorBody().toString())
                    }

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.d("RETROFIT", t.message.toString())
                        _loginResult.value = LoginResult(error = R.string.login_failed)
                    }

                })
    }
    private fun register(username: String, password: String) {
        Common.USER.createUser(UserRequest(username, password))
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                    ) {
                        Log.d("RETROFIT", "auth success")
                        LoginRepository.login(response.body() ?: UserResponse("no", 0, null))
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("RETROFIT", t.message.toString())
                        _loginResult.value = LoginResult(error = R.string.login_failed)
                    }
                })
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