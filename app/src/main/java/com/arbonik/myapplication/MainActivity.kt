package com.arbonik.myapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.arbonik.myapplication.network.models.login.UserActivation
import com.arbonik.myapplication.network.uriParseToUserActivation
import com.arbonik.myapplication.ui.privateoffice.data.LoginRepository

class MainActivity : AppCompatActivity() {

    val navController : NavController by lazy { findNavController(R.id.nav_host_fragment) }
    val navView: BottomNavigationView by lazy { findViewById(R.id.nav_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setupBottonNavigationView(){
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_tracks, R.id.navigation_calculator, R.id.navigationProfileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.isVisible = true
    }
}

