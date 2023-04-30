package com.example.education.di.modules.service.auth

import com.example.core.client.remote.IAuthRemoteClient
import com.example.core.service.auth.AuthService
import com.example.domain.service.auth.IAuthService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AuthServiceModule {

    @Provides
    @Reusable
    fun provideAuthService(
        client: IAuthRemoteClient
    ): IAuthService {
        return AuthService(client)
    }

}
