package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.auth.IAuthService

class ConfirmEmail(
    private val authService: IAuthService
) {

    suspend operator fun invoke(code: String, email: String, token: String) =
        authService.confirmEmail(code, email, token)
}
