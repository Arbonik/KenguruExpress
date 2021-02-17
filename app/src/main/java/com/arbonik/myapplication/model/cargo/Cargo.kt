package com.arbonik.myapplication.model.cargo

import androidx.room.*

@Entity(tableName = "cargoes")
@TypeConverters(TypeCargoConverter::class)
data class Cargo(
    val TYPE : CargoType = CargoType.DOCUMENT,
    val weight :String = "",
    val comment : String = "",
    val height : String ="",
    val width : String = "",
    val length : String = ""
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

class TypeCargoConverter{
    @TypeConverter
    fun fromCargoType(type : CargoType) : String {
        return type.toString()
    }
    @TypeConverter
    fun toCargoType(type : String) : CargoType{
        return CargoType.valueOf(type)
    }
}