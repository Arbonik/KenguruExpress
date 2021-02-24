package com.arbonik.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//класс для сохранения запросов в базу
@Entity(tableName = "localityTable")
data class LocalityPair(
    val fromCityId: Int? = null,
    val fromCityTitle: String? = null,
    val toCityId: Int? = null,
    val toCityTitle: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
