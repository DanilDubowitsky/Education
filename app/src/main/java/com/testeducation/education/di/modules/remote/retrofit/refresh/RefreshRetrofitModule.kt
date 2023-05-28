package com.testeducation.education.di.modules.remote.retrofit.refresh

import com.testeducation.core.client.remote.refresh.IRefreshRemoteClient
import com.testeducation.domain.config.IUserConfig
import com.testeducation.remote.BuildConfig
import com.testeducation.remote.client.retrofit.refresh.RefreshRetrofitClient
import com.testeducation.remote.interceptor.AccessTokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object RefreshRetrofitModule {

    @Provides
    @Singleton
    @Named(REFRESH_RETROFIT_NAME)
    fun provideRetrofit(
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHTTPClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(
                interceptor
            )
        }
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAccessTokenInterceptor(
        refreshRemoteClient: IRefreshRemoteClient,
        userConfig: IUserConfig
    ): AccessTokenInterceptor {
        return AccessTokenInterceptor(refreshRemoteClient, userConfig)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        @Named(REFRESH_RETROFIT_NAME) retrofit: Retrofit
    ): RefreshRetrofitClient {
        return retrofit.create(RefreshRetrofitClient::class.java)
    }

    const val REFRESH_RETROFIT_NAME = "REFRESH_RETROFIT"
    const val REFRESH_HTTP_CLIENT = "REFRESH_HTTP_CLIENT"

}