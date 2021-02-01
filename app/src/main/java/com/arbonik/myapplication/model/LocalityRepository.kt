package com.arbonik.myapplication.model

import com.arbonik.myapplication.network.Common

class LocalityRepository{
    val COUNT_IN_REQUEST = 10

    suspend fun localitySearch(location: String) = Common.GEOGRAPHY.locality(location, COUNT_IN_REQUEST)

    suspend fun oneLocationSearch(location : String) = Common.GEOGRAPHY.locality(location, 1)
}