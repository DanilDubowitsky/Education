package com.testeducation.remote.model.auth

import com.google.gson.annotations.SerializedName

data class RemoteToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
