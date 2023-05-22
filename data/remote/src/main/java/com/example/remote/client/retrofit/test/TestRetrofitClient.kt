package com.example.remote.client.retrofit.test

import com.example.remote.model.global.GenericResponse
import com.example.remote.model.test.RemoteTestShort
import retrofit2.Response
import retrofit2.http.GET

interface TestRetrofitClient {

    @GET("content/tests")
    suspend fun getTests(): Response<GenericResponse<List<RemoteTestShort>>>
}
