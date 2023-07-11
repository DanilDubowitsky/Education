package com.testeducation.remote

interface ITokenExpirationListener {
    fun onTokenExpired()
    fun setOnTokenExpired(listener: () -> Unit)
}
