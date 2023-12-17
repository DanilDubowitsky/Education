package com.testeducation.core.service.auth

import com.testeducation.core.client.remote.auth.IAuthRemoteClient
import com.testeducation.domain.config.user.IRegistrationConfig
import com.testeducation.domain.model.auth.Token
import com.testeducation.domain.service.auth.IAuthService

class AuthService(
    private val authRemoteClient: IAuthRemoteClient,
    private val registrationConfig: IRegistrationConfig
) : IAuthService {

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String = authRemoteClient.signUp(
        username,
        email,
        password,
        confirmPassword
    )

    override suspend fun confirmEmail(code: String, email: String, token: String) =
        authRemoteClient.confirmEmail(code, email, token)

    override suspend fun signIn(email: String, password: String): Token =
        authRemoteClient.signIn(email, password)

    override suspend fun sendCodeAgain(email: String) {
        val config = registrationConfig.getAll()
        signUp(
            username = config.userName,
            email = config.email,
            password = config.password,
            confirmPassword = config.passwordConfirm
        )
    }

    override suspend fun getResetPasswordToken(email: String, code: String): String =
        authRemoteClient.getResetPasswordToken(email, code)

    override suspend fun sendResetPasswordCode(email: String) =
        authRemoteClient.sendResetPasswordCode(email)

    override suspend fun resetPassword(
        email: String,
        code: String,
        newPassword: String,
        repeatedPassword: String
    ) = authRemoteClient.resetPassword(email, code, newPassword, repeatedPassword)
}
