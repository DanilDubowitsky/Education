package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.auth.IAuthService

class SendCodeAgain(
    private val authService: IAuthService
) {

    suspend operator fun invoke(email: String) =
        authService.sendCodeAgain(email)
}