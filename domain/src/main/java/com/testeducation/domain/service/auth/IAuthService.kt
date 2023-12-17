package com.testeducation.domain.service.auth

import com.testeducation.domain.model.auth.Token

interface IAuthService {

    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): String

    suspend fun confirmEmail(
        code: String,
        email: String,
        token: String
    )

    suspend fun signIn(
        email: String,
        password: String
    ): Token

    suspend fun sendCodeAgain(
        email: String
    )

    suspend fun getResetPasswordToken(
        email: String,
        code: String
    ): String

    suspend fun sendResetPasswordCode(
        email: String
    )

    suspend fun resetPassword(
        email: String,
        code: String,
        newPassword: String,
        repeatedPassword: String
    )

}
