package com.example.domain.model.user

data class User(
    val id: String,
    val email: String,
    val userName: String,
    val registryDate: Long,
    val roles: List<String>
)
