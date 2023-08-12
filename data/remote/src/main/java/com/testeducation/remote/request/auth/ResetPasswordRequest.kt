package com.testeducation.remote.request.auth

import com.google.gson.annotations.SerializedName

class ResetPasswordRequest(
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("confirmation_password")
    val repeatedPassword: String,
    @SerializedName("token")
    val token: String
)
