package com.arbonik.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arbonik.myapplication.model.FullRequest
import com.arbonik.myapplication.network.models.tariff.Data
import kotlinx.coroutines.selects.select

@Dao
interface TariffDao {

    @Insert
    fun insert(tariffData: Data)

    @Query("delete from deliveryData")
    fun deleteAll()

    @Query("select * from deliveryData")
    fun getAll():LiveData<List<Data>>

    @Query("select * from deliveryData where id = :tariffId")
    fun getById(tariffId : String):Data

    @Insert
    fun insert(tariffData: FullRequest)

    @Query("select * from requests")
    fun getAllRequested():List<FullRequest>
}