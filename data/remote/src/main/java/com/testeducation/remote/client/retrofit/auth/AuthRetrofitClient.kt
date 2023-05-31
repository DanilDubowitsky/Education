package com.testeducation.remote.client.retrofit.auth

import com.testeducation.remote.model.auth.RemoteToken
import com.testeducation.remote.model.global.GenericResponse
import com.testeducation.remote.request.auth.ConfirmEmailRequest
import com.testeducation.remote.request.auth.RefreshRequest
import com.testeducation.remote.request.auth.SignInRequest
import com.testeducation.remote.request.auth.SignUpRequest
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

    @POST("/auth/sign-in-password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<GenericResponse<RemoteToken>>

}
