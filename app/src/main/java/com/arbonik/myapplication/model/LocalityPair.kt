package com.arbonik.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//класс для сохранения запросов в базу
@Entity(tableName = "localityTable")
data class LocalityPair(
    val fromCityId: Int = 0,
    val toCityId: Int = 0
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
