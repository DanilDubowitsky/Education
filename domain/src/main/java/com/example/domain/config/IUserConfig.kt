package com.example.domain.config

interface IUserConfig {
    fun setToken(token: String)
    fun getToken(): String
    fun setRefreshToken(token: String)
    fun getRefreshToken(): String
}
