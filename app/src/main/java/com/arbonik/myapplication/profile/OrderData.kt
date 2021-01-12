package com.arbonik.myapplication.profile

data class OrderData(
    var name: String,
    var surname: String,
    var patronymic: String? = null,
    var phoneNumber: String,
    var company: String,
    var comment: String? = null
) {
    companion object {
        fun instance() = OrderData(
            "q", "q", "q", "q",
            "q", "q"
        )

    }
}

