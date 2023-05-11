package com.example.remote.client.retrofit

import com.example.remote.model.global.GenericResponse
import com.example.remote.request.auth.ConfirmEmailRequest
import com.example.remote.request.auth.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitClient {

    @POST("/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<GenericResponse<Unit>>

    @POST("/auth/confirm-sign-up")
    suspend fun confirmEmail(
        @Body confirmEmailRequest: ConfirmEmailRequest
    ): Response<GenericResponse<Unit>>

}
