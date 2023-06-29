package com.testeducation.remote.interceptor

import com.testeducation.core.app.IAppBuildConfigHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AppVersionInterceptor(
    private val buildConfigProvider: IAppBuildConfigHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return chain.proceed(addVersionHeader(request))
    }

    @Synchronized
    private fun addVersionHeader(request: Request): Request {
        return request.newBuilder()
            .addHeader("Version", buildConfigProvider.getAppVersion())
            .build()
    }

}
