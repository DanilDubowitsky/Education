package com.example.domain.cases.auth

import com.example.domain.service.auth.IAuthService

class ConfirmEmail(
    private val authService: IAuthService
) {

    suspend operator fun invoke(code: String) =
        authService.confirmEmail(code)
}
