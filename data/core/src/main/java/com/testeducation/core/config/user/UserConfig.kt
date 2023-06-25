package com.testeducation.core.config.user

import com.testeducation.core.config.IConfigSource
import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.domain.utils.MINUTE_IN_MILLIS
import com.testeducation.domain.utils.WEEK_IN_MILLIS

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

    override fun isRefreshTokenExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastRefreshTokenUpdateTime()
        return (currentTime - lastUpdateTime) > WEEK_IN_MILLIS
    }

    override fun setLastRefreshTokenUpdateTime(time: Long) {
        configSource.setLong(REFRESH_TOKEN_UPDATE_TIME, time)
    }

    override fun getLastRefreshTokenUpdateTime(): Long = configSource.getLong(
        REFRESH_TOKEN_UPDATE_TIME,
    )

    companion object {
        const val CONFIG_NAME = "EDUCATION_CONFIG_NAME"
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
        private const val REFRESH_TOKEN_UPDATE_TIME = "REFRESH_TOKEN_UPDATE_TIME"
    }

}
