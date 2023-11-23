package com.testeducation.core.repository.user

import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserStatistics
import com.testeducation.domain.repository.user.IUserRepository

class UserRepository(
    private val userRemoteSource: IUserRemoteSource
) : IUserRepository {

    override suspend fun getCurrentUser(): User =
        userRemoteSource.getCurrentUser()

    override suspend fun getUserStatistics(): UserStatistics = userRemoteSource.getUserStatistics()

    override suspend fun setAvatar(avatarId: Int) = userRemoteSource.setAvatar(avatarId)

}
