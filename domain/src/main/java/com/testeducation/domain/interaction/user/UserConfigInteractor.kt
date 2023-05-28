package com.testeducation.domain.interaction.user

import com.testeducation.domain.config.IUserConfig

class UserConfigInteractor(
    private val userConfig: IUserConfig
) {

    fun isRefreshTokenExpired() = userConfig.isRefreshTokenExpired()

}