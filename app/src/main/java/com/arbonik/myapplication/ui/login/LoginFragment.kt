package com.arbonik.myapplication.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.FragmentLoginBinding
import com.arbonik.myapplication.network.models.login.UserRequest

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginFragmentBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginFragmentBinding = FragmentLoginBinding.bind(view)

        loginFragmentBinding.toRegisterFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_registrationFragment)
        }

        loginFragmentBinding.loginButton.setOnClickListener {
            viewModel.login(
                loginFragmentBinding.usernameRegisterTextEdit.text.toString(),
                loginFragmentBinding.passwordRegisterTextEdit.text.toString()
            )
        }
        viewModel.loginResult.observe(viewLifecycleOwner){
            if (it.success !=null){
                findNavController().navigate(R.id.action_navigation_login_to_profileOrderFragment)
            }
            if (it.error != null){
                loginFragmentBinding.usernameRegisterTextEdit.error = it.error
            }
        }

        viewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginFormState.usernameError?.let {
                    loginFragmentBinding.usernameRegisterTextEdit.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    loginFragmentBinding.passwordRegisterTextEdit.error = getString(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.loginDataChanged(
                    loginFragmentBinding.usernameRegisterTextEdit.text.toString(),
                    loginFragmentBinding.passwordRegisterTextEdit.text.toString()
                )
            }
        }
        loginFragmentBinding.usernameRegisterTextEdit.addTextChangedListener(
            afterTextChangedListener
        )
        loginFragmentBinding.passwordRegisterTextEdit.addTextChangedListener(
            afterTextChangedListener
        )

    }
}