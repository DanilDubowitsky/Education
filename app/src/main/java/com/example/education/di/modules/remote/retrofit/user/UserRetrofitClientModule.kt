package com.example.education.di.modules.remote.retrofit.user

import com.example.remote.client.retrofit.user.UserRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object UserRetrofitClientModule {

    @Provides
    @Reusable
    fun provideUserRetrofitClient(
        retrofit: Retrofit
    ): UserRetrofitClient {
        return retrofit.create(UserRetrofitClient::class.java)
    }

}
