package com.example.core.service.auth

import com.example.core.client.remote.auth.IAuthRemoteClient
import com.example.domain.model.auth.Token
import com.example.domain.service.auth.IAuthService

class AuthService(
    private val authRemoteClient: IAuthRemoteClient
) : IAuthService {

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = authRemoteClient.signUp(
        username,
        email,
        password,
        confirmPassword
    )

    override suspend fun confirmEmail(code: String) =
        authRemoteClient.confirmEmail(code)

    override suspend fun signIn(email: String, password: String): Token =
        authRemoteClient.signIn(email, password)

}
