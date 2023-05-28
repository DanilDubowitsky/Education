package com.testeducation.education.di.modules.remote.retrofit.user

import com.testeducation.remote.client.retrofit.user.UserRetrofitClient
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
