package com.example.domain.model.auth

data class Token(
    val accessToken: String,
    val refreshToken: String
)