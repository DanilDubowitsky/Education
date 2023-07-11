package com.testeducation.domain.service

import kotlinx.coroutines.flow.Flow

interface IRefreshTokenExpirationHandler {
    suspend fun getTokenExpirationFlow(): Flow<Unit>
}
