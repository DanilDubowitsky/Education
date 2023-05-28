package com.testeducation.education.di.modules.domain.usecase.theme

import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.repository.theme.IThemeRepository
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
