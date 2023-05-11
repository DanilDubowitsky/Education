package com.example.remote.client.remote.auth

import com.example.core.client.remote.IAuthRemoteClient
import com.example.remote.client.retrofit.AuthRetrofitClient
import com.example.domain.exception.ServerException
import com.example.remote.model.global.GenericResponse
import com.example.remote.request.auth.ConfirmEmailRequest
import com.example.remote.request.auth.SignUpRequest
import com.example.remote.utils.JSONUtils.toJsonObject
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
            val errorBodyString = response.errorBody()?.string().orEmpty()
            val errorJson = toJsonObject<GenericResponse<Unit>>(errorBodyString)
            throw ServerException(errorJson.status?.message.orEmpty())
        }
    }

    override suspend fun confirmEmail(code: String) {
        val request = ConfirmEmailRequest(code)
        val response = authRetrofitClient.confirmEmail(request)
        if (!response.isSuccessful) {
            val errorBodyString = response.errorBody()?.string().orEmpty()
            val errorJson = toJsonObject<GenericResponse<Unit>>(errorBodyString)
            throw ServerException(errorJson.status?.message.orEmpty())
        }
    }

}
