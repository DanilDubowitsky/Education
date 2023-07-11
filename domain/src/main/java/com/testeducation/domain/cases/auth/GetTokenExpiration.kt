package com.testeducation.domain.cases.auth

import com.testeducation.domain.service.IRefreshTokenExpirationHandler

class GetTokenExpiration(
    private val tokenExpirationHandler: IRefreshTokenExpirationHandler
) {

    suspend operator fun invoke() = tokenExpirationHandler.getTokenExpirationFlow()
}