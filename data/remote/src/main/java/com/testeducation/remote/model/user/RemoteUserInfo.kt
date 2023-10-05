package com.testeducation.remote.model.user

import com.google.gson.annotations.SerializedName

data class RemoteUserInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String
)
