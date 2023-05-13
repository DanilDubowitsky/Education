package com.example.core.config

import com.example.domain.config.IUserConfig

class UserConfig(
    private val configSource: IConfigSource
) : IUserConfig {

    override fun setToken(token: String) {
        configSource.setString(TOKEN_KEY, token)
    }

    override fun getToken(): String =
        configSource.getString(TOKEN_KEY)

    override fun setRefreshToken(token: String) {
        configSource.setString(REFRESH_TOKEN_KEY, token)
    }

    override fun getRefreshToken(): String =
        configSource.getString(REFRESH_TOKEN_KEY)

    companion object {
        const val CONFIG_NAME = "EDUCATION_CONFIG_NAME"
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }

}
