package com.testeducation.remote.client.retrofit.internal

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.internal.RemoteAppVersion
import retrofit2.http.GET

interface BackendRetrofitClient {

    @GET("/api/internal/applicationVersions")
    suspend fun getApplicationVersion(): RemoteResponse<RemoteAppVersion>

}