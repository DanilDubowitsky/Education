package com.example.education.di.modules.remote.client.auth

import com.example.core.client.remote.IAuthRemoteClient
import com.example.remote.client.remote.auth.AuthRemoteClient
import com.example.remote.client.retrofit.AuthRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AuthClientModule {

    @Provides
    @Reusable
    fun provideAuthClient(
        authRetrofitClient: AuthRetrofitClient
    ): IAuthRemoteClient = AuthRemoteClient(authRetrofitClient)

}
