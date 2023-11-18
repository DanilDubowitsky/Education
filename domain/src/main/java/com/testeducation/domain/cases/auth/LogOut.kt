package com.testeducation.domain.cases.auth

import com.testeducation.domain.config.user.IUserConfig

class LogOut(
    private val userConfig: IUserConfig
) {
    suspend operator fun invoke() {
        userConfig.setRefreshToken("")
        userConfig.setToken("")
    }
}