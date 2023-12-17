package com.testeducation.remote.request.auth

import com.google.gson.annotations.SerializedName

class ResetPasswordRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("token")
    val token: String,
)
