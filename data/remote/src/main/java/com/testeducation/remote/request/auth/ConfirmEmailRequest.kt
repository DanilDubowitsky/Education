package com.testeducation.remote.request.auth

import com.google.gson.annotations.SerializedName

class ConfirmEmailRequest(
    @SerializedName("code")
    val code: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String,
)
