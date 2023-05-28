package com.example.domain.repository.user

import com.example.domain.model.user.User

interface IUserRepository {

    suspend fun getCurrentUser(): User

}