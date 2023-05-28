package com.testeducation.domain.repository.user

import com.testeducation.domain.model.user.User

interface IUserRepository {

    suspend fun getCurrentUser(): User

}