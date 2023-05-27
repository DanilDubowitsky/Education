package com.example.education.di.modules.domain.repository.theme

import com.example.core.repository.theme.ThemeRepository
import com.example.core.source.remote.theme.IThemeRemoteSource
import com.example.domain.repository.theme.IThemeRepository
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
