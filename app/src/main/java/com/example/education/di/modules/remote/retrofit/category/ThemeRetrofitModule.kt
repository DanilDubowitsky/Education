package com.example.education.di.modules.remote.retrofit.category

import com.example.remote.client.retrofit.category.ThemeRetrofitClient
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
