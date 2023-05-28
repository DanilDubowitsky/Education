package com.testeducation.education.di.modules.remote.client.refresh

import com.testeducation.core.client.remote.refresh.IRefreshRemoteClient
import com.testeducation.remote.client.remote.refresh.RefreshRemoteClient
import com.testeducation.remote.client.retrofit.refresh.RefreshRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object RefreshClientModule {

    @Provides
    @Reusable
    fun provideRefreshClient(
        refreshRetrofitClient: RefreshRetrofitClient
    ): IRefreshRemoteClient = RefreshRemoteClient(refreshRetrofitClient)
}
