package com.arbonik.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arbonik.myapplication.model.FullRequest
import com.arbonik.myapplication.model.LocalityPair
import com.arbonik.myapplication.model.cargo.Cargo
import com.arbonik.myapplication.network.models.ProductResponse
import com.arbonik.myapplication.network.models.tariff.Data
import com.arbonik.myapplication.profile.OrderData


@Database(
    entities = arrayOf(
        Data::class,
        Cargo::class,
        LocalityPair::class,
        ProductResponse::class,
        OrderData::class,
        FullRequest::class
    ), version = 1, exportSchema = false
)

abstract class KenguruDatabase : RoomDatabase() {
    abstract fun localityDao(): LocalityDao
    abstract fun tariffDao() : TariffDao
    abstract fun cargoDao() : CargoDao
}