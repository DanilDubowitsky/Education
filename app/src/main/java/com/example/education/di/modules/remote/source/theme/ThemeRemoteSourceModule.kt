package com.example.education.di.modules.remote.source.theme

import com.example.core.source.remote.theme.IThemeRemoteSource
import com.example.remote.client.retrofit.category.ThemeRetrofitClient
import com.example.remote.source.theme.ThemeRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ThemeRemoteSourceModule {

    @Provides
    @Reusable
    fun provideThemeRemoteSource(
        themeRetrofitClient: ThemeRetrofitClient
    ): IThemeRemoteSource = ThemeRemoteSource(themeRetrofitClient)

}
