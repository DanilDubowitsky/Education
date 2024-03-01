package com.testeducation.core.source.remote.user

interface IUserConfirmCodeRemoteSource {
    suspend fun deleteUser(): String
    suspend fun confirmDeleteUser(token: String, code: String)
}