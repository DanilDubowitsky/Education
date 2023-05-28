package com.testeducation.core.source.remote.user

import com.testeducation.domain.model.user.User

interface IUserRemoteSource {

    suspend fun getCurrentUser(): User

}
