package com.testeducation.education.di.modules.remote.theme

import com.testeducation.core.source.remote.theme.IThemeRemoteSource
import com.testeducation.remote.client.retrofit.theme.ThemeRetrofitClient
import com.testeducation.remote.source.theme.ThemeRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ThemeRemoteModule {

    @Provides
    @Reusable
    fun provideThemeRemoteSource(
        themeRetrofitClient: ThemeRetrofitClient
    ): IThemeRemoteSource = ThemeRemoteSource(themeRetrofitClient)

}
