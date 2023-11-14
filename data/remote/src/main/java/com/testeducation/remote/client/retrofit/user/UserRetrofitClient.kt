package com.testeducation.remote.client.retrofit.user

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.user.RemoteUser
import com.testeducation.remote.model.user.RemoteUserStatistics
import retrofit2.http.GET

interface UserRetrofitClient {

    @GET("/api/app/account")
    suspend fun getCurrentUser(): RemoteResponse<RemoteUser>

    @GET("/api/app/account/statistics")
    suspend fun getUserStatistics() : RemoteResponse<RemoteUserStatistics>

}