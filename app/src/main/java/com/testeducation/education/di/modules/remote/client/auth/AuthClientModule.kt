package com.testeducation.education.di.modules.remote.client.auth

import com.testeducation.core.client.remote.auth.IAuthRemoteClient
import com.testeducation.domain.config.user.IRegistrationConfig
import com.testeducation.domain.config.user.ITokenConfirmConfig
import com.testeducation.remote.client.remote.auth.AuthRemoteClient
import com.testeducation.remote.client.retrofit.auth.AuthRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AuthClientModule {

    @Provides
    @Reusable
    fun provideAuthClient(
        authRetrofitClient: AuthRetrofitClient,
        registrationConfig: IRegistrationConfig,
        tokenConfirmConfig: ITokenConfirmConfig
    ): IAuthRemoteClient =
        AuthRemoteClient(authRetrofitClient, registrationConfig, tokenConfirmConfig)

}
