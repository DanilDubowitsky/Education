package com.testeducation.remote.source.user

import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.domain.model.user.User
import com.testeducation.remote.client.retrofit.user.UserRetrofitClient
import com.testeducation.remote.converter.user.toModel
import com.testeducation.remote.utils.getResult

class UserRemoteSource(
    private val userRetrofitClient: UserRetrofitClient
) : IUserRemoteSource {

    override suspend fun getCurrentUser(): User =
        userRetrofitClient.getCurrentUser().getResult().data.toModel()
}