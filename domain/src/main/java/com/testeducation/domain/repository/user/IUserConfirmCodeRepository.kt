package com.testeducation.domain.repository.user

interface IUserConfirmCodeRepository {
    suspend fun deleteUser(): String

    suspend fun confirmDeleteUser(token: String, code: String)
}