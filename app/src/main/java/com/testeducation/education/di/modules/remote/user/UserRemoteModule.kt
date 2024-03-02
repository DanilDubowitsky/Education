package com.testeducation.education.di.modules.remote.user

import com.testeducation.core.source.remote.user.IUserConfirmCodeRemoteSource
import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.remote.client.retrofit.user.UserRetrofitClient
import com.testeducation.remote.source.user.UserConfirmCodeRemoteSource
import com.testeducation.remote.source.user.UserRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserRemoteModule {

    @Provides
    @Reusable
    fun provideUseRemoteSource(
        userRetrofitClient: UserRetrofitClient
    ): IUserRemoteSource = UserRemoteSource(userRetrofitClient)

    @Provides
    @Reusable
    fun provideUserConfirmCodeRepository(
        userRetrofitClient: UserRetrofitClient
    ) : IUserConfirmCodeRemoteSource = UserConfirmCodeRemoteSource(userRetrofitClient)

}
