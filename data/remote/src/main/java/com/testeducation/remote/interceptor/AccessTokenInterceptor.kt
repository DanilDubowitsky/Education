package com.testeducation.remote.interceptor

import com.testeducation.core.client.remote.refresh.IRefreshRemoteClient
import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.domain.exception.ServerException
import com.testeducation.remote.ITokenExpirationListener
import com.testeducation.remote.model.global.GenericResponse
import com.testeducation.remote.utils.JSONUtils
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.HttpURLConnection


class AccessTokenInterceptor(
    private val authRemoteClient: IRefreshRemoteClient,
    private val userConfig: IUserConfig,
    private val tokenExpirationListener: ITokenExpirationListener
) : Interceptor {

    private companion object {
        const val STATUS_CODE_ERROR_FIELDS = 6
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val userToken = userConfig.getToken()

        val response = chain.proceed(newRequestWithAccessToken(userToken, request))
        val responseBodyString = response.body?.string().orEmpty()
        val errorJson = JSONUtils.toJsonObject<GenericResponse<Unit>>(
            responseBodyString
        )
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED &&
            errorJson.status?.code != STATUS_CODE_ERROR_FIELDS
        ) {
            response.close()
            val newToken = getNewToken()
            return chain.proceed(newRequestWithAccessToken(newToken, request))
        }
        val body = responseBodyString.toResponseBody(response.body?.contentType())
        return response.newBuilder().body(body).build()
    }

    @Synchronized
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
