package com.testeducation.remote.source.user

import com.testeducation.core.source.remote.user.IUserConfirmCodeRemoteSource
import com.testeducation.remote.client.retrofit.user.UserRetrofitClient
import com.testeducation.remote.model.user.ConfirmUserRemote
import com.testeducation.remote.utils.getResult

class UserConfirmCodeRemoteSource(
    private val userRetrofitClient: UserRetrofitClient
) : IUserConfirmCodeRemoteSource {
    override suspend fun deleteUser(): String {
        return userRetrofitClient.deleteUser().getResult().data
    }

    override suspend fun confirmDeleteUser(token: String, code: String) {
        userRetrofitClient.confirmDeleteUser(
            bodyRequest = ConfirmUserRemote(
                code = code, token = token
            )
        ).getResult()
    }
}