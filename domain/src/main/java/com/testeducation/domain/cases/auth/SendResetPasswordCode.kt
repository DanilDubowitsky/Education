package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.auth.IAuthService

class SendResetPasswordCode(
    private val authService: IAuthService
) {

    suspend operator fun invoke(email: String) = authService.sendResetPasswordCode(email)
}
