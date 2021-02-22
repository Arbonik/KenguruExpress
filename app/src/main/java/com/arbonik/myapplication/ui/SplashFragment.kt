package com.arbonik.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.R
import com.arbonik.myapplication.network.uriParseToUserActivation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val flags =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity?.window?.decorView?.systemUiVisibility = flags

        //hide bar and navView
        (activity as? AppCompatActivity)?.apply {
            supportActionBar?.hide()
            findViewById<BottomNavigationView>(R.id.nav_view).isVisible = false
        }
        nextScreen()
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0

        (activity as? AppCompatActivity)?.apply {
            supportActionBar?.show()
        }
    }

    private fun nextScreen() {
        MainScope().launch(Dispatchers.IO) {
            delay(1500)
            //удаление фрагмента из backstack
            val deleteThisFragmentFromBackStack =
                findNavController().popBackStack(R.id.fullscreen_content, true)

            val activityData = requireActivity().intent.data
            //Если активити было открыто по ссылке для активации аккаунта
            if (activityData != null) {
                //анализируем строку
                val userActivation = uriParseToUserActivation(activityData.toString())
                KenguruApplication.loginRepository.activatedUser(userActivation)
            }
            if (KenguruApplication.loginRepository.isLoggedIn)
                findNavController().navigate(R.id.action_splashFragment_to_navigationProfileFragment)
            else if (deleteThisFragmentFromBackStack.not()) {
                findNavController().navigate(R.id.action_splashFragment_to_navigation_login)
            }
        }
    }
}