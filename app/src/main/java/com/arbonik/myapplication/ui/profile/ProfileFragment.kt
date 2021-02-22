package com.arbonik.myapplication.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.MainActivity
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.FragmentProfileBinding
import com.arbonik.myapplication.network.Resource
import com.arbonik.myapplication.network.models.login.ProfileData
import kotlin.system.exitProcess


class ProfileFragment : Fragment() {
    private val viewModel by viewModels<ProfileFragmentViewModel>()

    private lateinit var profileOrderBinding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).apply {
            setupBottonNavigationView()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profileOrderBinding = FragmentProfileBinding.bind(view)
        //Выбор даты рождения с помощью DatePicker
        profileOrderBinding.birthDayEditText.setOnClickListener { v ->
            AlertDialog.Builder(requireContext())
                .setView(
                    DatePicker(requireContext()). apply {
                        setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                            profileOrderBinding.birthDayEditText.setText("$dayOfMonth-$monthOfYear-$year")
                        }
                    }
                )
                .setPositiveButton("Выбрать", null)
                .create()
                .show()
        }
        viewModel.profileData.observe(viewLifecycleOwner) {
            profileOrderBinding.profileData = it
        }

        profileOrderBinding.updateProfileDataButton.setOnClickListener {
            viewModel.updateProfileData(
                ProfileData(
                    date_birth = profileOrderBinding.birthDayEditText.text.toString(),
                    email = null,
                    first_name = profileOrderBinding.name.text.toString(),
                    is_active = null,
                    is_fully_confirmed = null,
                    last_name = profileOrderBinding.surname.text.toString(),
                    patronymic = profileOrderBinding.patronymic.text.toString(),
                    phone = profileOrderBinding.phone.text.toString(),
                    phone_confirmed = null,
                    photo = null
                )
            )
        }
        viewModel.dataStatus.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                Log.d("GOOD", it.data!!)
            }
        }

        viewModel.loadProfileData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_menu) {
            AlertDialog.Builder(requireContext())
                .setTitle("Выйти из аккаунта?")
                .setPositiveButton("Да") {
                        dialog, which -> KenguruApplication.loginRepository.logout()
                    val intent = Intent(requireContext(),MainActivity::class.java)
                    startActivity(intent)
                    exitProcess(0)
                }
                .setNegativeButton("Нет", null)
                .create()
                .show()
        }
        return true
    }
}