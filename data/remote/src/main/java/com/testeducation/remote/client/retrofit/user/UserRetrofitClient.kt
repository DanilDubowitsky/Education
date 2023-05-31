package com.testeducation.remote.client.retrofit.user

import com.testeducation.remote.model.global.GenericResponse
import com.testeducation.remote.model.user.RemoteUser
import retrofit2.Response
import retrofit2.http.GET

interface UserRetrofitClient {

    @GET("/account")
    suspend fun getCurrentUser(): Response<GenericResponse<RemoteUser>>

}