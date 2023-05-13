package com.example.domain.cases.auth

import com.example.domain.config.IUserConfig
import com.example.domain.service.auth.IAuthService

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
    }
}