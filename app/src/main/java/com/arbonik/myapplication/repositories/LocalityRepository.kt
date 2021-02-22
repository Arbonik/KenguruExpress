package com.arbonik.myapplication.repositories

import com.arbonik.myapplication.database.KenguruDatabase
import com.arbonik.myapplication.model.LocalityPair
import com.arbonik.myapplication.network.Common

class LocalityRepository(database: KenguruDatabase){

    private val dao = database.localityDao()
    var fromCityId: Int = 1
    var toCityId: Int = 2

    var currentLocalityPair = LocalityPair(0,0)

    val COUNT_IN_REQUEST = 10

    suspend fun localitySearch(location: String) = Common.GEOGRAPHY.locality(location, COUNT_IN_REQUEST)

    suspend fun oneLocationSearch(location : String) = Common.GEOGRAPHY.locality(location, 1)

    fun insertLocalityPair(localityPair: LocalityPair) = dao.insert(localityPair)
}