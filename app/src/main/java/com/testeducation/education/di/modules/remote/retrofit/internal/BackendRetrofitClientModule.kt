package com.testeducation.education.di.modules.remote.retrofit.internal

import com.testeducation.remote.client.retrofit.internal.BackendRetrofitClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object BackendRetrofitClientModule {

    @Provides
    @Singleton
    fun provideBackendRetrofitClient(retrofit: Retrofit): BackendRetrofitClient =
        retrofit.create(BackendRetrofitClient::class.java)
}
