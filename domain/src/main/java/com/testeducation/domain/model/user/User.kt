package com.testeducation.domain.model.user

data class User(
    val id: String,
    val avatarId: Int,
    val email: String,
    val userName: String,
    val registryDate: Long,
    val roles: List<String>
)
