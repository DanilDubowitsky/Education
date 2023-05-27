package com.example.remote.source.user

import com.example.core.source.remote.user.IUserRemoteSource
import com.example.domain.model.user.User
import com.example.remote.client.retrofit.user.UserRetrofitClient
import com.example.remote.converter.user.toModel
import com.example.remote.utils.ResponseUtils.getResult

class UserRemoteSource(
    private val userRetrofitClient: UserRetrofitClient
) : IUserRemoteSource {

    override suspend fun getCurrentUser(): User =
        userRetrofitClient.getCurrentUser().getResult().data.toModel()
}