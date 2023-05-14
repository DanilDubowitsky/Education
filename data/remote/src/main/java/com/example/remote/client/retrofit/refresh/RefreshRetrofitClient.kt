package com.example.remote.client.retrofit.refresh

import com.example.remote.model.auth.RemoteToken
import com.example.remote.model.global.GenericResponse
import com.example.remote.request.auth.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshRetrofitClient {

    @POST("/auth/refresh")
    suspend fun refresh(
        @Body refreshRequest: RefreshRequest
    ): Response<GenericResponse<RemoteToken>>

}
