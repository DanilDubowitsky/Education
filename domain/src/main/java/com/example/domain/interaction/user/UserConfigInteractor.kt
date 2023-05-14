package com.example.domain.interaction.user

import com.example.domain.config.IUserConfig

class UserConfigInteractor(
    private val userConfig: IUserConfig
) {

    fun isRefreshTokenExpired() = userConfig.isRefreshTokenExpired()

}