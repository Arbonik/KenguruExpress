package com.arbonik.myapplication.product

import com.arbonik.myapplication.model.cargo.Cargo
import com.arbonik.myapplication.model.cargo.CargoType
import org.junit.Assert
import org.junit.Test

class CargoCreatorTest {

    val cargoWeight = 1
    val document = Cargo(
        CargoType.DOCUMENT,
        "1",
        "",
        "",
        "",
        ""
    )
    val cargo = Cargo(
        CargoType.CARGO,
        "1",
        "",
        "1.0",
        "1.0",
        "1.0"
    )

    @Test
    fun createDocument() {
        val doc = CargoCreator.createDocument(cargoWeight)
        Assert.assertEquals(document, doc)
    }
    @Test
    fun createCargo() {
        val cargoTest = CargoCreator.createCargo(cargoWeight, 1f, 1.0, "1.0")
        Assert.assertEquals(cargo, cargoTest)
    }
}