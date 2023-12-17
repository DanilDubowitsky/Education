package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.auth.IAuthService

class SignUp(
    private val authService: IAuthService
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String = authService.signUp(
        username,
        email,
        password,
        confirmPassword
    )
}
