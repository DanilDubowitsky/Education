package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.auth.IAuthService

class ResetPassword(
    private val authService: IAuthService
) {

    suspend operator fun invoke(
        email: String,
        newPassword: String,
        repeatedPassword: String,
        code: String
    ) = authService.resetPassword(email, code, newPassword, repeatedPassword)
}