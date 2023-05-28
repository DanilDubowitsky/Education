package com.example.remote.client.retrofit.category

import com.example.remote.model.global.GenericResponse
import com.example.remote.model.test.RemoteThemeShort
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ThemeRetrofitClient {

    @GET("content/themes")
    suspend fun getThemes(
        @Query("title") title: String,
        @Query("order") order: String,
        @Query("direction") direction: String
    ): Response<GenericResponse<List<RemoteThemeShort>>>

}
