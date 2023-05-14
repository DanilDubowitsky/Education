package com.example.education.di.modules.remote.retrofit

import com.example.education.di.modules.remote.retrofit.RefreshRetrofitModule.REFRESH_HTTP_CLIENT
import com.example.remote.BuildConfig
import com.example.remote.client.retrofit.auth.AuthRetrofitClient
import com.example.remote.interceptor.AccessTokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named(REFRESH_HTTP_CLIENT) httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named(REFRESH_HTTP_CLIENT)
    fun provideOkHTTPClient(tokenInterceptor: AccessTokenInterceptor): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(
                interceptor
            )
        }
        okHttpBuilder.addInterceptor(tokenInterceptor)
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(retrofit: Retrofit): AuthRetrofitClient {
        return retrofit.create(AuthRetrofitClient::class.java)
    }

}
