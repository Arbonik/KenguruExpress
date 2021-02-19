package com.arbonik.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arbonik.myapplication.model.LocalityPair

@Dao
interface LocalityDao {
    @Insert
    fun insert(locPair : LocalityPair)

    @Query("select * from localityTable")
    fun getAllPairs():List<LocalityPair>
}