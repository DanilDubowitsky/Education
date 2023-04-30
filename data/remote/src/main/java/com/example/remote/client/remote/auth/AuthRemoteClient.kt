package com.example.remote.client.remote.auth

import com.example.core.client.remote.IAuthRemoteClient
import com.example.remote.client.retrofit.AuthRetrofitClient
import com.example.remote.request.auth.SignUpRequest
import retrofit2.HttpException

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
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
    }

}
