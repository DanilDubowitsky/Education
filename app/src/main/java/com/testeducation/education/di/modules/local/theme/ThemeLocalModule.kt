package com.testeducation.education.di.modules.local.theme

import com.testeducation.core.source.local.theme.IThemeLocalSource
import com.testeducation.local.database.EducationDataBase
import com.testeducation.local.source.theme.ThemeLocalSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ThemeLocalModule {

    @Provides
    @Reusable
    fun provideThemeLocalSource(
        dataBase: EducationDataBase
    ): IThemeLocalSource = ThemeLocalSource(dataBase.themeShortDao)
}
