package com.testeducation.domain.service.auth

import com.testeducation.domain.model.auth.Token

interface IAuthService {

    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    )

    suspend fun confirmEmail(
        code: String,
        email: String
    )

    suspend fun signIn(
        email: String,
        password: String
    ): Token

}
