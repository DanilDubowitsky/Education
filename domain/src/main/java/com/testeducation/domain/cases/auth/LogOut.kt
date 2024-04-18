package com.testeducation.domain.cases.auth

import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.domain.database.IEducationDatabase

class LogOut(
    private val userConfig: IUserConfig,
    private val database: IEducationDatabase
) {
    suspend operator fun invoke() {
        database.clear()
        userConfig.setRefreshToken("")
        userConfig.setToken("")
        userConfig.setLastRefreshTokenUpdateTime(0L)
        userConfig.setAvatarVisibleScreen(false)
    }
}