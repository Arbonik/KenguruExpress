package com.arbonik.myapplication.repositories

import com.arbonik.myapplication.database.KenguruDatabase
import com.arbonik.myapplication.model.LocalityPair
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.geography.LocalityResponse

class LocalityRepository(database: KenguruDatabase) {

    private val dao = database.localityDao()

    var localityFrom: LocalityResponse? = null
    var localityTo: LocalityResponse? = null

    val COUNT_IN_REQUEST = 10


    fun saveAddresses(localityPair: LocalityPair) {

    }

    private fun insertLocalityPair(localityPair: LocalityPair) = dao.insert(localityPair)

    //functions fro search locality
    suspend fun localitySearch(location: String) =
        Common.GEOGRAPHY.locality(location, COUNT_IN_REQUEST)

    suspend fun oneLocationSearch(location: String) = Common.GEOGRAPHY.locality(location, 1)

    fun localityResponseToLocalityPair(
        from: LocalityResponse? = localityFrom,
        to: LocalityResponse? = localityTo
    ): LocalityPair {
        var lr = LocalityPair()
        if (from != null && to != null)
            lr = LocalityPair(
                from.last().id,
                from.last().full_title,
                to.last().id,
                to.last().full_title
            )
        return lr
    }

}