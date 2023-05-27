package com.example.core.source.remote.user

import com.example.domain.model.user.User

interface IUserRemoteSource {

    suspend fun getCurrentUser(): User

}
