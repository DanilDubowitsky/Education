package com.example.education.di.modules.remote.retrofit.test

import com.example.remote.client.retrofit.test.TestRetrofitClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object TestRetrofitClientModule {

    @Provides
    @Singleton
    fun provideTestRetrofitClient(
        retrofit: Retrofit
    ): TestRetrofitClient = retrofit.create(TestRetrofitClient::class.java)

}
