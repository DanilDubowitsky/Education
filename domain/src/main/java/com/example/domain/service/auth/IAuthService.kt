package com.example.domain.service.auth

import com.example.domain.model.auth.Token

interface IAuthService {

    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    )

    suspend fun confirmEmail(
        code: String
    )

    suspend fun signIn(
        email: String,
        password: String
    ): Token

}
