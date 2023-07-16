package com.testeducation.education.di.modules.core.repository.theme

import com.testeducation.core.repository.theme.ThemeRepository
import com.testeducation.core.source.local.theme.IThemeLocalSource
import com.testeducation.core.source.remote.theme.IThemeRemoteSource
import com.testeducation.domain.repository.theme.IThemeRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ThemeCoreModule {

    @Provides
    @Reusable
    fun provideThemeRepositoryModule(
        remoteSource: IThemeRemoteSource,
        localSource: IThemeLocalSource
    ): IThemeRepository = ThemeRepository(remoteSource, localSource)
}
