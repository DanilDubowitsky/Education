package com.example.core.client.remote

interface IAuthRemoteClient {
    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    )
}
