package com.testeducation.domain.cases.auth

import com.testeducation.domain.config.user.IUserConfig

class LogOut(
    private val userConfig: IUserConfig
) {
    operator fun invoke() {
        userConfig.setRefreshToken("")
        userConfig.setToken("")
        userConfig.setLastRefreshTokenUpdateTime(0L)
        userConfig.setAvatarVisibleScreen(false)
    }
}