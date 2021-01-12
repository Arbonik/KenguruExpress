package com.arbonik.myapplication.network

object Common {
    val USER : UserReference
        get() = RetrofitClient.retrofit!!.create(UserReference::class.java)

    val GEOGRAPHY : GeographyReference
        get() = RetrofitClient.retrofit!!.create(GeographyReference::class.java)

    val PRODUCT : ProductReference
        get() = RetrofitClient.retrofit!!.create(ProductReference::class.java)

    val SERVICES : ServicesReference
        get() = RetrofitClient.retrofit!!.create(ServicesReference::class.java)

    val DEPARTURES: DeparturesReference
        get() = RetrofitClient.retrofit!!.create(DeparturesReference::class.java)
}