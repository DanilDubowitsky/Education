package com.testeducation.remote.request.auth

import com.google.gson.annotations.SerializedName

class GetResetPasswordTokenRequest(
    @SerializedName("code")
    val code: String
)
