package com.testeducation.domain.config.user

import com.testeducation.domain.model.user.RegistrationConfigData

interface IRegistrationConfig {
    fun set(
        email: String,
        password: String,
        passwordConfirm: String,
        token: String,
        userName: String
    )

    fun getAll(): RegistrationConfigData
}