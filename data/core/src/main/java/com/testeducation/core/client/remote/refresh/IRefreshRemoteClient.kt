package com.testeducation.core.client.remote.refresh

import com.testeducation.domain.model.auth.Token

interface IRefreshRemoteClient {

    suspend fun refresh(refreshToken: String): Token
}
