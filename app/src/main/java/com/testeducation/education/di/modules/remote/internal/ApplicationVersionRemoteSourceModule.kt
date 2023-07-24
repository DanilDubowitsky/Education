package com.testeducation.education.di.modules.remote.internal

import com.testeducation.core.source.remote.internal.IAppVersionRemoteSource
import com.testeducation.remote.client.retrofit.internal.BackendRetrofitClient
import com.testeducation.remote.source.internal.AppVersionRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ApplicationVersionRemoteSourceModule {

    @Provides
    @Reusable
    fun provideAppVersionRemoteSource(
        backendRetrofitClient: BackendRetrofitClient
    ): IAppVersionRemoteSource = AppVersionRemoteSource(backendRetrofitClient)
}
