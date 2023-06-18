package com.testeducation.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UserAgentInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(setUserAgentHeader(request))
    }

    @Synchronized
    private fun setUserAgentHeader(request: Request): Request {
        return request.newBuilder()
            .addHeader("User-Agent", "application")
            .build()
    }

}
