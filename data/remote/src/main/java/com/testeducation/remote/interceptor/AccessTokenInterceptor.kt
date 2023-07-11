package com.testeducation.remote.interceptor

import com.testeducation.core.client.remote.refresh.IRefreshRemoteClient
import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.domain.exception.ServerException
import com.testeducation.remote.ITokenExpirationListener
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

class AccessTokenInterceptor(
    private val authRemoteClient: IRefreshRemoteClient,
    private val userConfig: IUserConfig,
    private val tokenExpirationListener: ITokenExpirationListener
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val userToken = userConfig.getToken()

        val response = chain.proceed(newRequestWithAccessToken(userToken, request))

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
            val newToken = getNewToken()
            return chain.proceed(newRequestWithAccessToken(newToken, request))
        }
        return response
    }

    private fun getNewToken(): String {
        return runBlocking {
            val refreshToken = userConfig.getRefreshToken()
            try {
                val newToken = authRemoteClient.refresh(refreshToken)
                userConfig.setToken(newToken.accessToken)
                userConfig.setRefreshToken(newToken.refreshToken)
                userConfig.setLastRefreshTokenUpdateTime(System.currentTimeMillis())
                newToken.accessToken
            } catch (e: ServerException) {
                tokenExpirationListener.onTokenExpired()
                throw e
            }
        }
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

}
