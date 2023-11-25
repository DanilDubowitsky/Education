package com.testeducation.remote.model.user

import com.google.gson.annotations.SerializedName

data class RemoteUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatar_id")
    val avatarId: Int,
    @SerializedName("username")
    val userName: String,
    @SerializedName("registrydate")
    val registryDate: Long,
    @SerializedName("roles")
    val roles: List<String>
)