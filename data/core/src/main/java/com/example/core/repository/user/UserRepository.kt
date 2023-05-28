package com.example.core.repository.user

import com.example.core.source.remote.user.IUserRemoteSource
import com.example.domain.model.user.User
import com.example.domain.repository.user.IUserRepository

class UserRepository(
    private val userRemoteSource: IUserRemoteSource
) : IUserRepository {

    override suspend fun getCurrentUser(): User =
        userRemoteSource.getCurrentUser()

}
