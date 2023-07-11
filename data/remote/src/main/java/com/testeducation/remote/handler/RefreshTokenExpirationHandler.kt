package com.testeducation.remote.handler

import com.testeducation.domain.service.IRefreshTokenExpirationHandler
import com.testeducation.remote.ITokenExpirationListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RefreshTokenExpirationHandler(
    private val tokenExpirationListener: ITokenExpirationListener
) : IRefreshTokenExpirationHandler {

    override suspend fun getTokenExpirationFlow(): Flow<Unit> = callbackFlow {
        tokenExpirationListener.setOnTokenExpired {
            trySend(Unit)
        }
        awaitClose()
    }

}
