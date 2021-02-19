package com.arbonik.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arbonik.myapplication.model.LocalityPair
import com.arbonik.myapplication.model.cargo.Cargo
import com.arbonik.myapplication.network.models.tariff.Data


@Database(
    entities = arrayOf(
        Data::class,
        Cargo::class,
        LocalityPair::class
    ), version = 1, exportSchema = false
)

abstract class KenguruDatabase : RoomDatabase() {
    abstract fun localityDao(): LocalityDao
    abstract fun tariffDao() : TariffDao
    abstract fun cargoDao() : CargoDao
}