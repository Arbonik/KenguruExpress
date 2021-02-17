package com.arbonik.myapplication.product

import com.arbonik.myapplication.model.cargo.Cargo
import com.arbonik.myapplication.model.cargo.CargoType
import com.arbonik.myapplication.network.models.ProductRequest


// user prototype pattern
class CargoCreator {
    companion object {
        private val prototype = Cargo(CargoType.DOCUMENT)
        fun <T> createDocument(cargoWeight: T): Cargo {
            return prototype.copy(
                weight = cargoWeight.toString()
            )
        }

        fun <T> createCargo(
            cargoWeight: T,
            cargoHeight: T,
            cargoWidth: T,
            cargoLength: T,
        ): Cargo {
            return prototype.copy(
                TYPE = CargoType.CARGO,
                weight = cargoWeight.toString(),
                width = cargoWidth.toString(),
                length = cargoLength.toString(),
                height = cargoHeight.toString(),
            )
        }
    }
}