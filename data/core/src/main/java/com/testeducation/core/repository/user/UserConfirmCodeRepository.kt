package com.testeducation.core.repository.user

import com.testeducation.core.source.remote.user.IUserConfirmCodeRemoteSource
import com.testeducation.domain.repository.user.IUserConfirmCodeRepository

class UserConfirmCodeRepository(private val remoteSource: IUserConfirmCodeRemoteSource) :
    IUserConfirmCodeRepository {
    override suspend fun deleteUser(): String {
        return remoteSource.deleteUser()
    }

    override suspend fun confirmDeleteUser(token: String, code: String) {
        remoteSource.confirmDeleteUser(token = token, code = code)
    }
}