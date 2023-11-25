package com.testeducation.core.source.remote.user

import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserStatistics

interface IUserRemoteSource {

    suspend fun getCurrentUser(): User

    suspend fun getUserStatistics(): UserStatistics

    suspend fun setAvatar(avatarId: Int)

    suspend fun sendSupport(text: String, category: String)
}
