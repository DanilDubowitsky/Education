package com.testeducation.remote.client.retrofit.user

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.user.RemoteUser
import retrofit2.http.GET

interface UserRetrofitClient {

    @GET("/api/app/account")
    suspend fun getCurrentUser(): RemoteResponse<RemoteUser>

}