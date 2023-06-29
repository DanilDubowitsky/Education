package com.testeducation.domain.config.user

interface IUserConfig {
    fun setToken(token: String)
    fun getToken(): String
    fun setRefreshToken(token: String)
    fun getRefreshToken(): String
    fun isRefreshTokenExpired(): Boolean
    fun setLastRefreshTokenUpdateTime(time: Long)
    fun getLastRefreshTokenUpdateTime(): Long
}