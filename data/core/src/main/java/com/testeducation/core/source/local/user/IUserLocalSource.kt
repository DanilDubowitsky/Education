package com.testeducation.core.source.local.user

interface IUserLocalSource {

    suspend fun getCurrentUser()
}
