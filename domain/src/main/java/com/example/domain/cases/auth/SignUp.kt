package com.example.domain.cases.auth

import com.example.domain.service.auth.IAuthService

class SignUp(
    private val authService: IAuthService
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = authService.signUp(
        username,
        email,
        password,
        confirmPassword
    )
}
