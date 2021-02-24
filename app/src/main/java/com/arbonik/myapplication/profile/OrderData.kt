package com.arbonik.myapplication.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orderData")
data class OrderData(
    var name: String ="",
    var surname: String = "",
    var patronymic: String? = null,
    var phoneNumber: String ="",
    var company: String ="",
    var comment: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
