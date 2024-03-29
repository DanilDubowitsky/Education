package com.testeducation.remote.client.retrofit.user

import com.testeducation.remote.model.global.RemoteResponse
import com.testeducation.remote.model.user.ConfirmUserRemote
import com.testeducation.remote.model.user.RemoteUser
import com.testeducation.remote.model.user.RemoteUserStatistics
import com.testeducation.remote.request.user.BugReportRequest
import com.testeducation.remote.request.user.SetAvatarRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserRetrofitClient {

    @GET("/api/app/account")
    suspend fun getCurrentUser(): RemoteResponse<RemoteUser>

    @GET("/api/app/account/statistics")
    suspend fun getUserStatistics() : RemoteResponse<RemoteUserStatistics>

    @POST("/api/app/account/avatar")
    suspend fun setAvatar(@Body avatar: SetAvatarRequest): RemoteResponse<Unit>

    @POST("/api/app/main/feedback")
    suspend fun sendBugReport(@Body request: BugReportRequest): RemoteResponse<Unit>

    @POST("/api/app/account/delete")
    suspend fun deleteUser(): RemoteResponse<String>

    @POST("/api/app/account/confirm-delete")
    suspend fun confirmDeleteUser(@Body bodyRequest: ConfirmUserRemote): RemoteResponse<Unit>

}