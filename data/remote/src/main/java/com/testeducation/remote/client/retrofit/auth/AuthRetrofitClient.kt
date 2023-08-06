package com.testeducation.remote.client.retrofit.auth

import com.testeducation.remote.model.auth.RemoteToken
import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.request.auth.ConfirmEmailRequest
import com.testeducation.remote.request.auth.SignInRequest
import com.testeducation.remote.request.auth.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitClient {

    @POST("/api/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): RemoteResponse<Unit>

    @POST("/api/auth/confirm-sign-up-code")
    suspend fun confirmEmail(
        @Query("email") email: String,
        @Body confirmEmailRequest: ConfirmEmailRequest
    ): RemoteResponse<Unit>

    @POST("/api/auth/sign-in-password")
    suspend fun signIn(@Body signInRequest: SignInRequest): RemoteResponse<RemoteToken>

    @POST("/api/auth/send-sign-up-code")
    suspend fun sendCodeAgain(@Query("email") email: String): RemoteResponse<Unit>

}
