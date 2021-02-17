package com.arbonik.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arbonik.myapplication.network.models.tariff.Data

@Dao
interface TariffDao {

    @Insert
    fun insert(tariffData: Data)

    @Query("delete from deliveryData")
    fun deleteAll()

    @Query("select * from deliveryData")
    fun getAll():LiveData<List<Data>>

}