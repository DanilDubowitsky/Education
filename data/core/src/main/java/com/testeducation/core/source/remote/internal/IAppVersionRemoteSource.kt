package com.testeducation.core.source.remote.internal

import com.testeducation.domain.model.internal.AppVersion

interface IAppVersionRemoteSource {

    suspend fun getActualAppVersion(): AppVersion
}
