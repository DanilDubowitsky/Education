package com.testeducation.remote.source.internal

import com.testeducation.core.source.remote.internal.IAppVersionRemoteSource
import com.testeducation.domain.model.internal.AppVersion
import com.testeducation.remote.client.retrofit.internal.BackendRetrofitClient
import com.testeducation.remote.converter.internal.toModel
import com.testeducation.remote.utils.getResult

class AppVersionRemoteSource(
    private val backendRetrofitClient: BackendRetrofitClient
) : IAppVersionRemoteSource {

    override suspend fun getActualAppVersion(): AppVersion =
        backendRetrofitClient.getApplicationVersion().getResult().data.toModel()
}
