package com.example.education.di.modules.remote.source.user

import com.example.core.source.remote.user.IUserRemoteSource
import com.example.remote.client.retrofit.user.UserRetrofitClient
import com.example.remote.source.user.UserRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserRemoteSourceModule {

    @Provides
    @Reusable
    fun provideUseRemoteSource(
        userRetrofitClient: UserRetrofitClient
    ): IUserRemoteSource = UserRemoteSource(userRetrofitClient)

}
