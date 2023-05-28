package com.example.education.di.modules.domain.usecase.theme

import com.example.domain.cases.theme.GetThemes
import com.example.domain.repository.theme.IThemeRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ThemeUseCaseModule {

    @Provides
    @Reusable
    fun provideGetThemes(
        themeRepository: IThemeRepository
    ): GetThemes = GetThemes(themeRepository)
}
