package com.example.core.service.auth

import com.example.core.client.remote.IAuthRemoteClient
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

}
