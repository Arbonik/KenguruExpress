package com.arbonik.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arbonik.myapplication.model.cargo.Cargo
import kotlinx.coroutines.flow.Flow

@Dao
interface CargoDao {
    @Query("SELECT * FROM cargoes")
//    suspend fun getAll():Flow<Cargo>
    suspend fun getAll():List<Cargo>

    @Insert
    fun insertCargo(cargo : Cargo)



}