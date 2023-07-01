package com.testeducation.remote.client.retrofit.test

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.test.RemotePage
import com.testeducation.remote.model.test.RemoteTestShort
import retrofit2.http.GET
import retrofit2.http.Query

interface TestRetrofitClient {

    @GET("/api/app/content/tests")
    suspend fun getTests(
        @Query("title") query: String?,
        @Query("theme") theme: String?,
        @Query("order") order: String?,
        @Query("direction") direction: String?,
        @Query("min_time") minTime: Int,
        @Query("max_time") maxTime: Int,
        @Query("has_limit") hasLimit: Boolean,
        @Query("min_questions") minQuestions: Int,
        @Query("max_questions") maxQuestions: Int,
        @Query("page_index") index: Int,
        @Query("page_size") limit: Int,
    ): RemoteResponse<RemotePage<RemoteTestShort>>
}
