package com.example.remote.request.auth

import com.google.gson.annotations.SerializedName

class RefreshRequest(
    @SerializedName("refresh_token")
    val refreshToken: String
)