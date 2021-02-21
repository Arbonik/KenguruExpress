package com.arbonik.myapplication.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.RegistationFragmentBinding
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : Fragment() {
    private val viewModel by viewModels<RegistationViewModel>()
    private lateinit var registrationFragmentBinding: RegistationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.registation_fragment, container, false)
        registrationFragmentBinding = RegistationFragmentBinding.bind(root)

        registrationFragmentBinding.registerButton.setOnClickListener {
        if (registrationFragmentBinding.passwordRegisterTextEdit.text.toString() == registrationFragmentBinding.passwordRetry.text.toString())
                viewModel.registrationNewUser(
                    registrationFragmentBinding.usernameRegisterTextEdit.text.toString(),
                    registrationFragmentBinding.passwordRegisterTextEdit.text.toString()
                )
            it.clearFocus()
            }

        viewModel.registrationStatus.observe(viewLifecycleOwner){
            if (it.success != null){
                registrationFragmentBinding.passwordRegisterTextEdit.isActivated = false
                registrationFragmentBinding.passwordRetry.isActivated = false
                registrationFragmentBinding.registerButton.isActivated = false
                registrationFragmentBinding.usernameRegisterTextEdit.isActivated = false
                Snackbar.make(requireContext(), root, "Вы успешно зарегестрированы, подтвердите свой аккаунт!", Snackbar.LENGTH_SHORT)
                    .show()
            }
            if (it.error !=null){
                Snackbar.make(requireContext(), root, it.error, Snackbar.LENGTH_SHORT).show()
            }
        }

        return root
    }

}