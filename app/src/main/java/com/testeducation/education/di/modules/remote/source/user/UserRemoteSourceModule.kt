package com.testeducation.education.di.modules.remote.source.user

import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.remote.client.retrofit.user.UserRetrofitClient
import com.testeducation.remote.source.user.UserRemoteSource
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
