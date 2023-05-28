package com.testeducation.education.di.modules.service.auth

import com.testeducation.core.client.remote.auth.IAuthRemoteClient
import com.testeducation.core.service.auth.AuthService
import com.testeducation.domain.service.auth.IAuthService
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
