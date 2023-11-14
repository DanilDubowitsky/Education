package com.testeducation.domain.repository.user

import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserStatistics

interface IUserRepository {

    suspend fun getCurrentUser(): User

    suspend fun getUserStatistics(): UserStatistics

}