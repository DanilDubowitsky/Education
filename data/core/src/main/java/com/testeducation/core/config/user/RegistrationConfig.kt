package com.testeducation.core.config.user

import com.testeducation.core.config.IConfigSource
import com.testeducation.domain.config.user.IRegistrationConfig
import com.testeducation.domain.model.user.RegistrationConfigData

class RegistrationConfig(
    private val configSource: IConfigSource
) : IRegistrationConfig {
    companion object {
        const val NAME = "RegistrationConfig"
        const val EMAIL_KEY = "REGISTRATION_EMAIL_KEY"
        const val PASSWORD_KEY = "REGISTRATION_PASSWORD_KEY"
        const val PASSWORD_CONFIRM_KEY = "REGISTRATION_PASSWORD_CONFIRM_KEY"
        const val TOKEN_KEY = "REGISTRATION_TOKEN_KEY"
        const val USERNAME_KEY = "REGISTRATION_USERNAME_KEY"
        const val TIME_CREATED_KEY = "REGISTRATION_TIME_CREATED"
    }

    override fun set(
        email: String,
        password: String,
        passwordConfirm: String,
        token: String,
        userName: String
    ) {
        configSource.setString(EMAIL_KEY, email)
        configSource.setString(PASSWORD_KEY, password)
        configSource.setString(PASSWORD_CONFIRM_KEY, passwordConfirm)
        configSource.setString(TOKEN_KEY, token)
        configSource.setString(USERNAME_KEY, userName)
        configSource.setLong(TIME_CREATED_KEY, System.currentTimeMillis())
    }

    override fun getAll(): RegistrationConfigData {
        return RegistrationConfigData(
            configSource.getString(EMAIL_KEY),
            configSource.getString(PASSWORD_KEY),
            configSource.getString(PASSWORD_CONFIRM_KEY),
            configSource.getString(TOKEN_KEY),
            configSource.getString(USERNAME_KEY),
            configSource.getLong(TIME_CREATED_KEY),
        )
    }

    override fun clear() {
        set("", "", "", "", "")
    }
}