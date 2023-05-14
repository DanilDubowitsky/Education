package com.example.core.client.remote.refresh

import com.example.domain.model.auth.Token

interface IRefreshRemoteClient {

    suspend fun refresh(refreshToken: String): Token
}
