package com.example.remote.client.retrofit

import com.example.remote.model.GenericResponse
import com.example.remote.request.auth.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitClient {

    @POST("/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<GenericResponse<Unit>>

}
