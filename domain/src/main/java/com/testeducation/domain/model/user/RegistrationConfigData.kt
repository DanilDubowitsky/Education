package com.testeducation.domain.model.user

data class RegistrationConfigData(
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val token: String,
    val userName: String,
    val timeCreated: Long
)
