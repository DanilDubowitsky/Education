package com.testeducation.remote.client.retrofit.theme

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.test.RemoteThemeShort
import retrofit2.http.GET
import retrofit2.http.Query

interface ThemeRetrofitClient {

    @GET("/api/app/content/themes")
    suspend fun getThemes(
        @Query("title") title: String,
        @Query("order") order: String,
        @Query("direction") direction: String
    ): RemoteResponse<List<RemoteThemeShort>>

}
