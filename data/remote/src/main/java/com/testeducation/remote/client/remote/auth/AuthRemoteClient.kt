package com.testeducation.remote.client.remote.auth

import com.testeducation.core.client.remote.auth.IAuthRemoteClient
import com.testeducation.domain.model.auth.Token
import com.testeducation.remote.client.retrofit.auth.AuthRetrofitClient
import com.testeducation.remote.converter.auth.toModel
import com.testeducation.remote.request.auth.ConfirmEmailRequest
import com.testeducation.remote.request.auth.SignInRequest
import com.testeducation.remote.request.auth.SignUpRequest
import com.testeducation.remote.utils.getResult

class AuthRemoteClient(
    private val authRetrofitClient: AuthRetrofitClient
) : IAuthRemoteClient {

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val request = SignUpRequest(username, email, password, confirmPassword)
        val response = authRetrofitClient.signUp(request)
        return response.getResult()
    }

    override suspend fun confirmEmail(code: String) {
        val request = ConfirmEmailRequest(code)
        val response = authRetrofitClient.confirmEmail(request)
        return response.getResult()
    }

    override suspend fun signIn(email: String, password: String): Token {
        val request = SignInRequest(email, password)
        val response = authRetrofitClient.signIn(request)
        return response.getResult().data.toModel()
    }

}