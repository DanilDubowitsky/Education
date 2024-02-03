package com.testeducation.remote.client.retrofit.question

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.question.RemoteQuestion
import com.testeducation.remote.request.question.QuestionCreateRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface QuestionRetrofitClient {

    @POST("/api/app/content/tests/{testId}/questions")
    suspend fun createQuestion(
        @Path("testId") id: String,
        @Body request: QuestionCreateRequest
    ): RemoteResponse<Unit>

    @PUT("/api/app/content/tests/{testId}/questions/{questionId}")
    suspend fun updateQuestion(
        @Path("testId") id: String,
        @Path("questionId") questionId: String,
        @Body request: QuestionCreateRequest
    ): RemoteResponse<Unit>

    @GET("/api/app/content/tests/{testId}/questions")
    suspend fun getQuestions(
        @Path("testId") testId: String
    ): RemoteResponse<List<RemoteQuestion>>

    @DELETE("/api/app/content/tests/{testId}/questions/{questionId}")
    suspend fun deleteQuestion(
        @Path("testId") testId: String,
        @Path("questionId") questionId: String
    ): RemoteResponse<Unit>

    @GET("/api/app/content/tests/{testId}/questions/{questionId}")
    suspend fun getQuestions(
        @Path("testId") testId: String,
        @Path("questionId") questionId: String
    ): RemoteResponse<RemoteQuestion>
}