package com.example.domain.config

interface IUserConfig {
    suspend fun setToken(token: String)
    suspend fun getToken(): String
    suspend fun setRefreshToken(token: String)
    suspend fun getRefreshToken(): String
}
