package com.example.education.di.modules.remote.client.refresh

import com.example.core.client.remote.refresh.IRefreshRemoteClient
import com.example.remote.client.remote.refresh.RefreshRemoteClient
import com.example.remote.client.retrofit.refresh.RefreshRetrofitClient
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
