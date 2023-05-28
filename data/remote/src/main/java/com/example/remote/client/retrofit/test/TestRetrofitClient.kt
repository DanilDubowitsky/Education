package com.example.remote.client.retrofit.test

import com.example.remote.model.global.GenericResponse
import com.example.remote.model.test.RemoteTestShort
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestRetrofitClient {

    @GET("content/tests")
    suspend fun getTests(
        @Query("title") query: String?,
        @Query("theme") theme: String?,
        @Query("order") order: String?,
        @Query("direction") direction: String?,
    ): Response<GenericResponse<List<RemoteTestShort>>>
}
