package com.testeducation.remote.source.user

import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserStatistics
import com.testeducation.remote.client.retrofit.user.UserRetrofitClient
import com.testeducation.remote.converter.user.toModel
import com.testeducation.remote.converter.user.userStatisticsToModel
import com.testeducation.remote.request.user.BugReportRequest
import com.testeducation.remote.request.user.SetAvatarRequest
import com.testeducation.remote.utils.getResult

class UserRemoteSource(
    private val userRetrofitClient: UserRetrofitClient
) : IUserRemoteSource {

    override suspend fun getCurrentUser(): User =
        userRetrofitClient.getCurrentUser().getResult().data.toModel()

    override suspend fun getUserStatistics(): UserStatistics =
        userRetrofitClient.getUserStatistics().getResult().data.userStatisticsToModel()

    override suspend fun setAvatar(avatarId: Int) {
        userRetrofitClient.setAvatar(
            SetAvatarRequest(avatarId)
        ).getResult()
    }

    override suspend fun sendSupport(text: String, category: String) {
        userRetrofitClient.sendBugReport(
            BugReportRequest(
                category, text
            )
        ).getResult()
    }
}