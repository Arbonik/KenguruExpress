package com.arbonik.myapplication.network.data.geography

data class LocalityItem(
    val area: String?,
    val area_type: String?,
    val country: String?,
    val country_iso_code: String?,
    val fias: String?,
    val full_title: String?,
    val id: String?,
    val kladr: String?,
    val latitude: String?,
    val locality: String?,
    val locality_type: String?,
    val longitude: String?,
    val okato: String?,
    val oktmo: String?,
    val postcode: String?,
    val region: String?,
    val region_type: String?
)



fun instanceEmptyLocalityItem() = LocalityItem(null, null,null,
        null,null,null,
        null,null,null,
        null,null,null,
        null, null, null,
        null,null)