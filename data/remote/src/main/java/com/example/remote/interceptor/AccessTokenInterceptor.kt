package com.example.remote.interceptor

import com.example.core.client.remote.refresh.IRefreshRemoteClient
import com.example.domain.config.IUserConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

class AccessTokenInterceptor(
    private val authRemoteClient: IRefreshRemoteClient,
    private val userConfig: IUserConfig
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val userToken = userConfig.getToken()

        val response = chain.proceed(newRequestWithAccessToken(userToken, request))

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val newToken = getNewToken()
            return chain.proceed(newRequestWithAccessToken(newToken, request))
        }
        return response
    }

    private fun getNewToken(): String {
        return synchronized(this) {
            runBlocking {
                val refreshToken = userConfig.getRefreshToken()
                val newToken = authRemoteClient.refresh(refreshToken)
                userConfig.setToken(newToken.accessToken)
                userConfig.setRefreshToken(newToken.refreshToken)
                newToken.accessToken
            }
        }
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()


}
