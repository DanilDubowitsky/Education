package com.testeducation.education.di.modules.remote.retrofit.global

import com.testeducation.core.app.IAppBuildConfigHelper
import com.testeducation.education.di.modules.remote.retrofit.refresh.RefreshRetrofitModule.REFRESH_HTTP_CLIENT
import com.testeducation.remote.BuildConfig
import com.testeducation.remote.client.retrofit.auth.AuthRetrofitClient
import com.testeducation.remote.interceptor.AccessTokenInterceptor
import com.testeducation.remote.interceptor.AppVersionInterceptor
import com.testeducation.remote.interceptor.UserAgentInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object RetrofitModule {

    private const val TIMEOUT = 60L

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
    fun provideOkHTTPClient(
        tokenInterceptor: AccessTokenInterceptor,
        appVersionInterceptor: AppVersionInterceptor,
        userAgentInterceptor: UserAgentInterceptor
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(
                interceptor
            )
        }
        okHttpBuilder.addInterceptor(tokenInterceptor)
        okHttpBuilder.addInterceptor(appVersionInterceptor)
        okHttpBuilder.addInterceptor(userAgentInterceptor)
        okHttpBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS).connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAppVersionInterceptor(
        buildConfigHelper: IAppBuildConfigHelper
    ): AppVersionInterceptor =
        AppVersionInterceptor(buildConfigHelper)

    @Provides
    @Singleton
    fun provideUserAgentInterceptor(): UserAgentInterceptor = UserAgentInterceptor()

    @Provides
    @Singleton
    fun provideRetrofitClient(retrofit: Retrofit): AuthRetrofitClient {
        return retrofit.create(AuthRetrofitClient::class.java)
    }

}
