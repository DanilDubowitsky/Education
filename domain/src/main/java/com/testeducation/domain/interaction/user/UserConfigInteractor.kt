package com.testeducation.domain.interaction.user

import com.testeducation.domain.config.user.IUserConfig

class UserConfigInteractor(
    private val userConfig: IUserConfig
) {

    fun isRefreshTokenExpired() = userConfig.isRefreshTokenExpired()

}