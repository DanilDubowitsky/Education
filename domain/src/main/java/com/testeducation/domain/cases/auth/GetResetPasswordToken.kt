package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.auth.IAuthService

class GetResetPasswordToken(
    private val authService: IAuthService
) {

    suspend operator fun invoke(
        email: String,
        code: String
    ) = authService.getResetPasswordToken(email, code)
}