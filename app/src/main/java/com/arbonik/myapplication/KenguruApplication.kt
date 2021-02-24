package com.arbonik.myapplication

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.arbonik.myapplication.database.KenguruDatabase
import com.arbonik.myapplication.repositories.LocalityRepository
import com.arbonik.myapplication.repositories.DeparturesRepository
import com.arbonik.myapplication.repositories.UserRepository
import com.arbonik.myapplication.repositories.ProductRepository

class KenguruApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object{
        private lateinit var appContext: Context

        val database: KenguruDatabase by lazy {
            val LOCK = Any()
            synchronized(LOCK) {
                Room.databaseBuilder(
                    appContext,
                    KenguruDatabase::class.java,
                    "database"
                )
                    .build()
            }
        }

        val localityRepository by lazy { LocalityRepository(database) }
        val departuresRepository by lazy { DeparturesRepository(database) }
        val productRepository by lazy {  ProductRepository() }
        val loginRepository by lazy { UserRepository(appContext) }
    }
}