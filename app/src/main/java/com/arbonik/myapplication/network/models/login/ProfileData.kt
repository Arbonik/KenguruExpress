package com.arbonik.myapplication.network.models.login

data class ProfileData(
    var date_birth: String?,
    var email: String?,
    var first_name: String?,
    var is_active: Boolean?,
    var is_fully_confirmed: Boolean?,
    var last_name: String?,
    var patronymic: String?,
    var phone: String?,
    var phone_confirmed: Boolean?,
    var photo: String?
)