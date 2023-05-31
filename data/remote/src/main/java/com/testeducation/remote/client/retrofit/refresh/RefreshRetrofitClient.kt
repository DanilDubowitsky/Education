package com.testeducation.remote.client.retrofit.refresh

import com.testeducation.remote.model.auth.RemoteToken
import com.testeducation.remote.model.global.GenericResponse
import com.testeducation.remote.request.auth.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshRetrofitClient {

    @POST("/auth/refresh")
    suspend fun refresh(
        @Body refreshRequest: RefreshRequest
    ): Response<GenericResponse<RemoteToken>>

}
