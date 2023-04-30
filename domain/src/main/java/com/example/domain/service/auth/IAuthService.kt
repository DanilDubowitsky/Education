package com.example.domain.service.auth

interface IAuthService {
    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    )
}
