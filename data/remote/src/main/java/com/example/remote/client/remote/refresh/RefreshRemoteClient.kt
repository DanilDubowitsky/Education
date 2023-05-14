package com.example.remote.client.remote.refresh

import com.example.core.client.remote.refresh.IRefreshRemoteClient
import com.example.domain.model.auth.Token
import com.example.remote.client.retrofit.refresh.RefreshRetrofitClient
import com.example.remote.converter.auth.toModel
import com.example.remote.request.auth.RefreshRequest
import com.example.remote.utils.ResponseUtils.getResult

class RefreshRemoteClient(
    private val refreshRetrofitClient: RefreshRetrofitClient
) : IRefreshRemoteClient {

    override suspend fun refresh(refreshToken: String): Token {
        val request = RefreshRequest(refreshToken)
        val response = refreshRetrofitClient.refresh(request)
        return response.getResult().data.toModel()
    }

}
