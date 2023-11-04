package com.testeducation.remote.client.retrofit.test

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.test.RemoteCreationTest
import com.testeducation.remote.model.test.RemotePage
import com.testeducation.remote.model.test.RemoteTest
import com.testeducation.remote.model.test.RemoteTestSettingsItem
import com.testeducation.remote.model.test.RemoteTestShort
import com.testeducation.remote.request.test.TestCreationRequest
import com.testeducation.remote.request.test.TestStyleRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TestRetrofitClient {

    @GET("/api/app/content/tests")
    suspend fun getTests(
        @Query("title") query: String?,
        @Query("theme") theme: String?,
        @Query("order") order: String?,
        @Query("direction") direction: String?,
        @Query("min_time") minTime: Int?,
        @Query("max_time") maxTime: Int?,
        @Query("has_limit") hasLimit: Boolean,
        @Query("min_questions") minQuestions: Int?,
        @Query("max_questions") maxQuestions: Int?,
        @Query("offset") offset: Int,
        @Query("page_size") limit: Int,
        @Query("owner") userId: String?
    ): RemoteResponse<RemotePage<RemoteTestShort>>

    @PUT("/api/app/account/tests/likes/{id}")
    suspend fun likeTest(@Path("id") id: String): RemoteResponse<Unit>

    @DELETE("/api/app/account/tests/likes/{id}")
    suspend fun unlikeTest(@Path("id") id: String): RemoteResponse<Unit>

    @GET("/api/app/account/tests/likes")
    suspend fun getLikedTests(
        @Query("title") query: String?,
        @Query("theme") theme: String?,
        @Query("order") order: String?,
        @Query("direction") direction: String?,
        @Query("min_time") minTime: Int?,
        @Query("max_time") maxTime: Int?,
        @Query("has_limit") hasLimit: Boolean,
        @Query("min_questions") minQuestions: Int?,
        @Query("max_questions") maxQuestions: Int?,
        @Query("offset") offset: Int,
        @Query("page_size") limit: Int,
    ): RemoteResponse<RemotePage<RemoteTestShort>>

    @POST("/api/app/content/tests")
    suspend fun createTest(@Body testCreationRequest: TestCreationRequest): RemoteResponse<RemoteCreationTest>

    @GET("/api/app/account/tests")
    suspend fun getCreatedTests(
        @Query("title") query: String?,
        @Query("theme") theme: String?,
        @Query("order") order: String?,
        @Query("direction") direction: String?,
        @Query("min_time") minTime: Int?,
        @Query("max_time") maxTime: Int?,
        @Query("has_limit") hasLimit: Boolean,
        @Query("min_questions") minQuestions: Int?,
        @Query("max_questions") maxQuestions: Int?,
        @Query("offset") offset: Int,
        @Query("page_size") limit: Int,
    ): RemoteResponse<RemotePage<RemoteTestShort>>

    @GET("/api/app/account/tests/passes")
    suspend fun getPassedTests(
        @Query("title") query: String?,
        @Query("theme") theme: String?,
        @Query("order") order: String?,
        @Query("direction") direction: String?,
        @Query("min_time") minTime: Int?,
        @Query("max_time") maxTime: Int?,
        @Query("has_limit") hasLimit: Boolean,
        @Query("min_questions") minQuestions: Int?,
        @Query("max_questions") maxQuestions: Int?,
        @Query("offset") offset: Int,
        @Query("page_size") limit: Int,
    ): RemoteResponse<RemotePage<RemoteTestShort>>

    @GET("/api/app/content/tests/{id}")
    suspend fun getTest(@Path("id") id: String) : RemoteResponse<RemoteTest>

    @GET("/api/app/content/tests/{id}/settings")
    suspend fun getTestSettings(@Path("id") id: String) : RemoteResponse<RemoteTestSettingsItem>

    @PUT("/api/app/content/tests/{id}")
    suspend fun updateTestSettings(@Path("id") id: String, @Body testSettingsItem: RemoteTestSettingsItem) : RemoteResponse<Unit>

    @PUT("/api/app/content/tests/{id}/style")
    suspend fun updateTestStyle(@Path("id") id: String, @Body remoteTestStyle: TestStyleRequest) : RemoteResponse<Unit>

    @POST("/api/app/content/tests/{testId}/publish")
    suspend fun publish(@Path("testId") id: String) : RemoteResponse<Unit>

    @POST("/api/app/content/tests/{testId}/unpublish")
    suspend fun draft(@Path("testId") id: String) : RemoteResponse<Unit>
}
