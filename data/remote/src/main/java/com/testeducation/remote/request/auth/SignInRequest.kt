package com.testeducation.remote.request.auth

import com.google.gson.annotations.SerializedName

class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
