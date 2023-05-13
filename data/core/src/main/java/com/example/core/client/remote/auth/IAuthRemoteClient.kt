package com.example.core.client.remote.auth

import com.example.domain.model.auth.Token

interface IAuthRemoteClient {

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
