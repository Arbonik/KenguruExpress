package com.arbonik.myapplication.network.data

data class DeparturesRequest(
    val cargoes: List<Int>?,
    val delivery: Boolean?,
    val pickup: Boolean?,
    val receiver_city: Int?,
    val sender_city: Int?,
    val services: List<Int>?
)