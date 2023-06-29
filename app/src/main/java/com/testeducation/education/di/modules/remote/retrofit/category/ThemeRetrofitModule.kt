package com.testeducation.education.di.modules.remote.retrofit.category

import com.testeducation.remote.client.retrofit.theme.ThemeRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object ThemeRetrofitModule {
    
    @Provides
    @Reusable
    fun provideThemeRetrofit(
        retrofit: Retrofit
    ): ThemeRetrofitClient = retrofit.create(ThemeRetrofitClient::class.java)
    
}
