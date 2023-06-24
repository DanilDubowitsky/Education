package com.testeducation.domain.cases.auth

import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.domain.service.auth.IAuthService

class SignIn(
    private val authService: IAuthService,
    private val userConfig: IUserConfig
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ) {
        val token = authService.signIn(email, password)
        userConfig.setRefreshToken(token.refreshToken)
        userConfig.setToken(token.accessToken)
        userConfig.setLastRefreshTokenUpdateTime(System.currentTimeMillis())
    }
}