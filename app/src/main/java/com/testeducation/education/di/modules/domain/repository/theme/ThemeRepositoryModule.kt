package com.testeducation.education.di.modules.domain.repository.theme

import com.testeducation.core.repository.theme.ThemeRepository
import com.testeducation.core.source.remote.theme.IThemeRemoteSource
import com.testeducation.domain.repository.theme.IThemeRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ThemeRepositoryModule {

    @Provides
    @Reusable
    fun provideThemeRepositoryModule(
        remoteSource: IThemeRemoteSource
    ): IThemeRepository = ThemeRepository(remoteSource)
}
