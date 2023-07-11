package com.testeducation.education.di.modules.remote.helper

import com.testeducation.domain.service.IRefreshTokenExpirationHandler
import com.testeducation.remote.ITokenExpirationListener
import com.testeducation.remote.handler.RefreshTokenExpirationHandler
import com.testeducation.remote.handler.TokenExpirationListener
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
object RemoteHelperModule {

    @Provides
    @Singleton
    fun provideTokenExpirationListener(): ITokenExpirationListener = TokenExpirationListener()

    @Provides
    @Reusable
    fun provideTokenExpirationHandler(
        listener: ITokenExpirationListener
    ): IRefreshTokenExpirationHandler = RefreshTokenExpirationHandler(listener)

}
