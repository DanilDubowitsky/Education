package com.testeducation.core.config.user

import com.testeducation.core.config.IConfigSource
import com.testeducation.domain.config.user.ITokenConfirmConfig

class TokenConfirmConfig(private val configSource: IConfigSource): ITokenConfirmConfig {

    companion object {
        const val NAME = "TokenConfirmConfig"
        const val TOKEN_KEY = "CONFIRM_TOKEN_KEY"
        const val EMAIL_KEY = "CONFIRM_EMAIL_KEY"
    }
    override fun set(token: String, email: String) {
        configSource.setString(TOKEN_KEY, token)
        configSource.setString(EMAIL_KEY, email)
    }

    override fun get(): String {
        return configSource.getString(TOKEN_KEY)
    }

    override fun getEmail(): String {
        return configSource.getString(EMAIL_KEY)
    }
}