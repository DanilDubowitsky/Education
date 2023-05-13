package com.example.remote.request.auth

import com.google.gson.annotations.SerializedName

class SignUpRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirmation_password")
    val confirmationPassword: String
)
