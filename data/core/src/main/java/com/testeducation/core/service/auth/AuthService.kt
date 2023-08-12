package com.testeducation.core.service.auth

import com.testeducation.core.client.remote.auth.IAuthRemoteClient
import com.testeducation.domain.model.auth.Token
import com.testeducation.domain.service.auth.IAuthService

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

    override suspend fun confirmEmail(code: String, email: String) =
        authRemoteClient.confirmEmail(code, email)

    override suspend fun signIn(email: String, password: String): Token =
        authRemoteClient.signIn(email, password)

    override suspend fun sendCodeAgain(email: String) =
        authRemoteClient.sendCodeAgain(email)

    override suspend fun getResetPasswordToken(email: String, code: String): String =
        authRemoteClient.getResetPasswordToken(email, code)

    override suspend fun sendResetPasswordCode(email: String) =
        authRemoteClient.sendResetPasswordCode(email)

    override suspend fun resetPassword(
        email: String,
        token: String,
        newPassword: String,
        repeatedPassword: String
    ) = authRemoteClient.resetPassword(email, token, newPassword, repeatedPassword)
}
