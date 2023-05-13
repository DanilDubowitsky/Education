package com.example.remote.client.remote.auth

import com.example.core.client.remote.auth.IAuthRemoteClient
import com.example.remote.client.retrofit.auth.AuthRetrofitClient
import com.example.domain.model.auth.Token
import com.example.remote.converter.auth.toModel
import com.example.remote.request.auth.ConfirmEmailRequest
import com.example.remote.request.auth.SignInRequest
import com.example.remote.request.auth.SignUpRequest
import com.example.remote.utils.ResponseUtils.getResult

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
