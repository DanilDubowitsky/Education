package com.testeducation.remote.client.remote.auth

import com.testeducation.core.client.remote.auth.IAuthRemoteClient
import com.testeducation.domain.config.user.IRegistrationConfig
import com.testeducation.domain.config.user.ITokenConfirmConfig
import com.testeducation.domain.model.auth.Token
import com.testeducation.remote.client.retrofit.auth.AuthRetrofitClient
import com.testeducation.remote.converter.auth.toModel
import com.testeducation.remote.request.auth.ConfirmEmailRequest
import com.testeducation.remote.request.auth.GetResetPasswordTokenRequest
import com.testeducation.remote.request.auth.ResetPasswordRequest
import com.testeducation.remote.request.auth.SignInRequest
import com.testeducation.remote.request.auth.SignUpRequest
import com.testeducation.remote.utils.getResult

class AuthRemoteClient(
    private val authRetrofitClient: AuthRetrofitClient,
    private val registrationConfig: IRegistrationConfig,
    private val tokenConfirm: ITokenConfirmConfig
) : IAuthRemoteClient {

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String {
        val request = SignUpRequest(username, email, password, confirmPassword)
        val response = authRetrofitClient.signUp(request)
        val token = response.getResult().data
        registrationConfig.set(
            email, password, confirmPassword, token, username
        )
        return token
    }

    override suspend fun confirmEmail(code: String, email: String, token: String) {
        val request = ConfirmEmailRequest(code, email, token)
        val response = authRetrofitClient.confirmEmail(request)
        return response.getResult()
    }

    override suspend fun signIn(email: String, password: String): Token {
        val request = SignInRequest(email, password)
        val response = authRetrofitClient.signIn(request)
        return response.getResult().data.toModel()
    }

    override suspend fun sendCodeAgain(email: String) =
        authRetrofitClient.sendCodeAgain(email).getResult()

    override suspend fun getResetPasswordToken(email: String, code: String): String {
        val request = GetResetPasswordTokenRequest(code)
        val response = authRetrofitClient.getResetPasswordToken(email, request)
        return response.getResult().data
    }

    override suspend fun sendResetPasswordCode(email: String) {
        val responseToken = authRetrofitClient.sendResetPasswordCode(email).getResult()
        tokenConfirm.set(responseToken.data, email)
    }

    override suspend fun resetPassword(
        email: String,
        code: String,
        newPassword: String,
        repeatedPassword: String
    ) {
        val token = tokenConfirm.get()
        val request = ResetPasswordRequest(email, newPassword, code, token)
        return authRetrofitClient.resetPassword(request).getResult()
    }
}
