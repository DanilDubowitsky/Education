package com.testeducation.remote.client.remote.refresh

import com.testeducation.core.client.remote.refresh.IRefreshRemoteClient
import com.testeducation.domain.model.auth.Token
import com.testeducation.remote.client.retrofit.refresh.RefreshRetrofitClient
import com.testeducation.remote.converter.auth.toModel
import com.testeducation.remote.request.auth.RefreshRequest
import com.testeducation.remote.utils.getResult

class RefreshRemoteClient(
    private val refreshRetrofitClient: RefreshRetrofitClient
) : IRefreshRemoteClient {

    override suspend fun refresh(refreshToken: String): Token {
        val request = RefreshRequest(refreshToken)
        val response = refreshRetrofitClient.refresh(request)
        return response.getResult().data.toModel()
    }

}
